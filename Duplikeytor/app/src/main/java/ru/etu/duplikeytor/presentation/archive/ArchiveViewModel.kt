package ru.etu.duplikeytor.presentation.archive

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.domain.models.Key
import ru.etu.duplikeytor.domain.repository.KeyRepository
import ru.etu.duplikeytor.domain.usecases.ShareUsecase
import ru.etu.duplikeytor.presentation.archive.model.KeyState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState
import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig
import ru.etu.duplikeytor.presentation.holder.model.AppEvent
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.KeyType
import ru.etu.duplikeytor.presentation.shared.model.Screen
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
internal class ArchiveViewModel @Inject constructor(
    private val shareUsecase: ShareUsecase,
    private val keyRepository: KeyRepository
) : ViewModel(), Screen {

    override var statusBarState = MutableStateFlow<StatusBarState>(
        StatusBarState.Title(
            title = "",
            requiredDisplay = true,
        )
    )
    override var navigationBarState = MutableStateFlow(
        NavigationBarState.build()
    )
    override val screenType: ScreenType = ScreenType.ARCHIVE

    override fun onBackClick(): Boolean = returnToPreviousState()

    override fun notifyResolveEvent(event: AppEvent) {
        if (event !is AppEvent.Archive) return
        when(event) {
            is AppEvent.Archive.KeySaved -> {
                when(_state.value) {
                    is KeyArchiveState.KeysList -> {
                        loadKeysFromArchive()
                    }
                    is KeyArchiveState.Key -> {
                        if ((_state.value as KeyArchiveState.Key).key.id == event.id) {
                            loadKeyFromArchive(event.id)
                        }
                        loadKeysFromArchive()
                    }
                }
            }
        }
    }

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyy в HH:mm")

    private val _keysState = MutableStateFlow<List<KeyState>>(emptyList())

    private val keyArchiveState = KeyArchiveState.KeysList(
        keys = _keysState.value,
        title = "Мои ключи",
    )

    private val _state = MutableStateFlow<KeyArchiveState>(keyArchiveState)

    val state: StateFlow<KeyArchiveState> = _state

    init {
        loadKeysFromArchive()

        viewModelScope.launch {
            _keysState
                .filter { _state.value is KeyArchiveState.KeysList }
                .collect { keys ->
                    _state.emit(
                        KeyArchiveState.KeysList(
                            keys = keys,
                            title = "Мои ключи",
                        )
                    )
                }
        }

        viewModelScope.launch {
            _state.collect { state ->
                statusBarState.emit(
                    StatusBarState.Title(
                        title = when(state) {
                            is KeyArchiveState.KeysList -> state.title
                            is KeyArchiveState.Key -> state.title
                        },
                        requiredDisplay = when(state) {
                            is KeyArchiveState.KeysList -> true
                            is KeyArchiveState.Key -> true
                        },
                        onBackClick = when (state) {
                            is KeyArchiveState.Key -> {{ returnToPreviousState() }}
                            is KeyArchiveState.KeysList -> null
                        }
                    )
                )
            }
        }
    }

    internal fun onKeySelected(id: Long) {
        loadKeyFromArchive(id)
    }

    internal fun onKeyDelete(id: Long, onSuccess: () -> Unit = {}) {
        returnToPreviousState()
        viewModelScope.launch {
            keyRepository.deleteKey(id)
            delay(10)
        }.invokeOnCompletion {
            onSuccess()
            loadKeysFromArchive()
        }
    }

    internal fun onKeyShare(context: Context, key: KeyState) {
        val keyType = key.type.toString()
        val keyName = key.name
        val pinsInfo = key.pins

        shareUsecase.share(
            context,
            title = "Отправить информацию о ключе",
            message = """
                Ключ: $keyName
                Тип ключа: $keyType
                Параметры: $pinsInfo
            """.trimIndent()
        )
    }

    private fun returnToPreviousState(): Boolean {
        return when(state.value) {
            is KeyArchiveState.KeysList -> false
            is KeyArchiveState.Key -> {
                changeState(
                    KeyArchiveState.KeysList(
                        keys = _keysState.value,
                        title = "Мои ключи",
                    )
                )
                true
            }
        }
    }

    private fun changeState(state: KeyArchiveState) =
        viewModelScope.launch { _state.emit(state) }

    private fun mapToKeyState(key: Key): KeyState {
        val type = KeyType.valueOf(key.type)
        return with(key) {
            KeyState(
                id = id,
                name = name,
                scale = scale,
                imageUri = photoUri,
                createdAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(createdAt),
                    ZoneId.systemDefault()
                ).format(formatter),
                type = type,
                pins = pins.joinToString(separator = "-"),
                config = type.toConfig(pins),
            )
        }
    }

    private fun KeyType.toConfig(pins: List<Int>) =
        when(this) {
            KeyType.KWIKSET -> {
                KeyConfig.Kwikset(pins = pins)
            }
            KeyType.SCHLAGE -> {
                KeyConfig.Schlage(pins = pins)
            }
        }

    private fun loadKeysFromArchive() {
        viewModelScope.launch {
            val keyStates = keyRepository.getKeys().map { key ->
                mapToKeyState(key)
            }
            _keysState.value = keyStates
        }
    }

    private fun loadKeyFromArchive(id: Long) {
        viewModelScope.launch {
            val key = with(keyRepository.getKey(id)) {
                KeyArchiveState.Key(
                    key = mapToKeyState(this!!),
                    title = name,
                )
            }
            _state.emit(key)
        }
    }
}