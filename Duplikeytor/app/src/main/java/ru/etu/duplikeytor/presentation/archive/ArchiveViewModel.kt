package ru.etu.duplikeytor.presentation.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.domain.repository.KeyRepository
import ru.etu.duplikeytor.presentation.archive.keycard.KeyState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.KeyType
import ru.etu.duplikeytor.presentation.shared.model.Screen
import java.util.Date
import javax.inject.Inject

@HiltViewModel
internal class ArchiveViewModel @Inject constructor(
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

    private val _keysState = MutableStateFlow<List<KeyState>>(emptyList())

    private val keyArchiveState = KeyArchiveState.KeysList(
        keys = _keysState.value,
        title = "Мои ключи",
    )

    private val _state = MutableStateFlow<KeyArchiveState>(keyArchiveState)

    val state: StateFlow<KeyArchiveState> = _state

    init {
        getKeysFromArchive() // TODO: получать ключи из репозитория
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
                            is KeyArchiveState.Key -> {{ _state.value = keyArchiveState }}
                            is KeyArchiveState.KeysList -> null
                        }
                    )
                )
            }
        }
    }

    internal fun onKeySelected(key: KeyState) {
        _state.value = KeyArchiveState.Key(
            key = key,
            title = key.name,
        )
    }

    internal fun onKeyDelete(id: Long) {
        // TODO: change screen & refresh state
        viewModelScope.launch {
            keyRepository.deleteKey(id)
        }
        getKeysFromArchive()
    }

    private fun returnToPreviousState(): Boolean {
        return when(state.value) {
            is KeyArchiveState.KeysList -> false
            is KeyArchiveState.Key -> true.also {
                changeState(
                    KeyArchiveState.KeysList(
                        keys = _keysState.value,
                        title = "Мои ключи",
                    )
                )
            }
        }
    }

    private fun changeState(state: KeyArchiveState) =
        viewModelScope.launch { _state.emit(state) }

    private fun getKeysFromArchive() {
        viewModelScope.launch {
            val keyStates = keyRepository.getKeys().map { key ->
                KeyState(
                    id = key.id,
                    name = key.name,
                    imageUri = key.photoUri,
                    createdAt = Date(key.createdAt).toString(), // TODO: Pretty transform
                    type = KeyType.KWIKSET, // TODO: from string to KeyType
                    pins = key.pins.toString(), // TODO: delete that cringe
                )
            }
            // TODO: убрать код скнизу тут кринж
            _keysState.value = keyStates
            changeState(
                KeyArchiveState.KeysList(
                    keys = _keysState.value,
                    title = "Мои ключи",
                )
            )
        }
    }
}