package ru.etu.duplikeytor.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.chose.KeyChosenState
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

    private val keys
        get () = getKeyTypes()

    private var keyChosen: KeyChosenState? = null
    private var keyScale: Float? = null


    private val _state: MutableStateFlow<CreateScreenState> =
        MutableStateFlow(CreateScreenState.Choose(keys = keys))
    val state: StateFlow<CreateScreenState> = _state

    internal fun onKeyChoose(key: KeyChosenState) {
        keyChosen = key
        viewModelScope.launch {
            _state.emit(CreateScreenState.Scale(key = key))
        }
    }

    internal fun onKeyScaled(scale: Float) {
        keyScale = scale
        viewModelScope.launch {
            _state.emit(CreateScreenState.Change())
        }
    }

    private fun getKeyTypes() = listOf(
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
            title = "Kwikset"
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
            title = "SCHLAGE"
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
            title = "Дима"
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
            title = "Kwikset."
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
            title = "SCHLAGE."
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
            title = "Дима."
        ),
    )
}