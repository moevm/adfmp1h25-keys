package ru.etu.duplikeytor.presentation.create

import androidx.lifecycle.ViewModel
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
}