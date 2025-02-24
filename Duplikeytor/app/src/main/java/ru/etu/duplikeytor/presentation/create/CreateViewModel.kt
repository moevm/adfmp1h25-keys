package ru.etu.duplikeytor.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.chose.KeyChooseState
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

internal class CreateViewModel @Inject constructor() : ViewModel(), Screen {

    // переработать логику для разных шагов
    override var statusBarState: StatusBarState = StatusBarState.Title(
        title = "Создать ключ",
        requiredDisplay = true,
    )
    override var navigationBarState: NavigationBarState = NavigationBarState.build()

    override val screenType: ScreenType = ScreenType.CREATE

    private val _state: MutableStateFlow<CreateScreenState> =
        MutableStateFlow(CreateScreenState.Choose(
            keys = listOf(
                KeyChooseState(
                    imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
                    title = "Kwikset"
                ),
                KeyChooseState(
                    imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                    title = "SCHLAGE"
                ),
                KeyChooseState(
                    imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
                    title = "Дима"
                ),
                KeyChooseState(
                    imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
                    title = "Kwikset."
                ),
                KeyChooseState(
                    imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                    title = "SCHLAGE."
                ),
                KeyChooseState(
                    imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
                    title = "Дима."
                ),
            ),
        ))

    val state: StateFlow<CreateScreenState> = _state

    internal fun onKeyChoose(key: KeyChooseState) {
        viewModelScope.launch {
            _state.emit(CreateScreenState.Scale(key = key))
        }
    }
}