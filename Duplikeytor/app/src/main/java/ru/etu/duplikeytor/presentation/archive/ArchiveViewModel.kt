package ru.etu.duplikeytor.presentation.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.archive.keycard.KeyState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

internal class ArchiveViewModel @Inject constructor() : ViewModel(), Screen {
    // Мусорные данные
    private val keyArchiveState = KeyArchiveState.KeysList(
        keys = listOf(
            KeyState(
                name = "Key 1",
                imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                createdAt = "10.10.2021 - 13:37",
                type = "Kwikset",
                pins = "1-2-3-4-5",
            ),
            KeyState(
                name = "Key 2",
                imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                createdAt = "10.11.2021 - 13:37",
                type = "Kwikset",
                pins = "1-2-3-4-5",
            ),
            KeyState(
                name = "Key 3",
                imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                createdAt = "10.12.2021 - 13:37",
                type = "Kwikset",
                pins = "1-2-3-4-5",
            ),
        ),
        title = "Мои ключи",
    )

    override var statusBarState: StatusBarState = StatusBarState.Title(
        title = "",
        requiredDisplay = true,
    )
    override var navigationBarState: NavigationBarState = NavigationBarState.build()
    override val screenType: ScreenType = ScreenType.ARCHIVE

    private val _state = MutableStateFlow<KeyArchiveState>(keyArchiveState)

    val state: StateFlow<KeyArchiveState> = _state

    init {
        // TODO фиксануть баг с названием ключа
        viewModelScope.launch {
            state.collect {
                statusBarState = StatusBarState.Title(
                    title = when(it) {
                        is KeyArchiveState.KeysList -> it.title
                        is KeyArchiveState.Key -> it.title
                    },
                    requiredDisplay = true,
                )
            }
        }
    }

    internal fun onKeySelected(key: KeyState) {
        _state.value = KeyArchiveState.Key(
            keyState = key,
            title = key.name,
        )
    }

    internal fun onKeyListSelected(keys: List<KeyState>, title: String) {
        _state.value = KeyArchiveState.KeysList(
            keys = keys,
            title = title,
        )
    }
}