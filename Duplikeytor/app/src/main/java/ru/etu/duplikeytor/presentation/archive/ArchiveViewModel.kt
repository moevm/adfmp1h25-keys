package ru.etu.duplikeytor.presentation.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.archive.keycard.KeyState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState
import ru.etu.duplikeytor.presentation.shared.model.KeyType
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

internal class ArchiveViewModel @Inject constructor() : ViewModel(), Screen {

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

    private val keysList = getKeysFromArchive()
    private val keyArchiveState = KeyArchiveState.KeysList(
        keys = keysList,
        title = "Мои ключи",
    )
    private val _state = MutableStateFlow<KeyArchiveState>(keyArchiveState)

    val state: StateFlow<KeyArchiveState> = _state

    init {
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

    private fun returnToPreviousState(): Boolean {
        return when(state.value) {
            is KeyArchiveState.KeysList -> false
            is KeyArchiveState.Key -> true.also {
                changeState(
                    KeyArchiveState.KeysList(
                        keys = keysList,
                        title = "Мои ключи",
                    )
                )
            }
        }
    }

    private fun changeState(state: KeyArchiveState) =
        viewModelScope.launch { _state.emit(state) }

    private fun getKeysFromArchive() = listOf( // TODO получаем из БД
        KeyState(
            name = "Сарай",
            imageUri = "https://images.kwikset.com/is/image/Kwikset/05991-mk-any_c3?wid=600&qlt=90&resMode=sharp",
            createdAt = "10.10.2024 - 13:37",
            type = KeyType.KWIKSET,
            pins = "1-2-3-4-5",
        ),
        KeyState(
            name = "Баня",
            imageUri = "https://images.kwikset.com/is/image/Kwikset/05991-mk-any_c3?wid=600&qlt=90&resMode=sharp",
            createdAt = "10.11.2024 - 13:37",
            type = KeyType.KWIKSET,
            pins = "2-3-4-5-1",
        ),
        KeyState(
            name = "Гараж",
            imageUri = "https://cdn.mscdirect.com/global/images/ProductImages/1716860-21.jpg",
            createdAt = "10.12.2024 - 13:37",
            type = KeyType.SCHLAGE,
            pins = "3-4-5-1-2",
        ),
    )
}