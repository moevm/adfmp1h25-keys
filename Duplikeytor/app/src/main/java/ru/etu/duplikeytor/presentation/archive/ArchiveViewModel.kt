package ru.etu.duplikeytor.presentation.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.archive.keycard.KeyState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState
import ru.etu.duplikeytor.presentation.archive.model.KeyType
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

internal class ArchiveViewModel @Inject constructor() : ViewModel(), Screen {

    private val keyArchiveState = KeyArchiveState.KeysList(
        keys = getKeysFromArchive(),
        title = "Мои ключи",
    )

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

    private fun getKeysFromArchive() = listOf( // TODO получаем из БД
        KeyState(
            name = "Key 1",
            imageUri = "https://ybis.ru/wp-content/uploads/2023/09/solntse-kartinka-1.webp",
            createdAt = "10.10.2021 - 13:37",
            type = KeyType.KWIKSET,
            pins = "1-2-3-4-5",
        ),
        KeyState(
            name = "Key 2",
            imageUri = "https://ybis.ru/wp-content/uploads/2023/09/solntse-kartinka-1.webp",
            createdAt = "10.11.2021 - 13:37",
            type = KeyType.KWIKSET,
            pins = "2-3-4-5-1",
        ),
        KeyState(
            name = "Key 3",
            imageUri = "https://ybis.ru/wp-content/uploads/2023/09/solntse-kartinka-1.webp",
            createdAt = "10.12.2021 - 13:37",
            type = KeyType.KWIKSET,
            pins = "3-4-5-1-2",
        ),
    )
}