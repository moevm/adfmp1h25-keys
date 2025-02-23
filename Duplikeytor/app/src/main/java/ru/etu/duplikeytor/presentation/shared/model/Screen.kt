package ru.etu.duplikeytor.presentation.shared.model

import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState

interface Screen {
    val screenType: ScreenType
    var statusBarState: StatusBarState
    var navigationBarState: NavigationBarState
}