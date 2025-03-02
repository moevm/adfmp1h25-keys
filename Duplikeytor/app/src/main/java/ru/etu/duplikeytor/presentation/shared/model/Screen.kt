package ru.etu.duplikeytor.presentation.shared.model

import kotlinx.coroutines.flow.MutableStateFlow
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState

interface Screen {
    val screenType: ScreenType
    var statusBarState: MutableStateFlow<StatusBarState>
    var navigationBarState: MutableStateFlow<NavigationBarState>
    fun onBackClick(): Boolean
}