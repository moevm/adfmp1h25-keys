package ru.etu.duplikeytor.presentation.navigation.model

import ru.etu.duplikeytor.presentation.uiKit.button.ButtonState

data class NavigationBarState(
    val buttons: List<ButtonState.Icon.Navigation>,
    val initSelectedIndex: Int = 0,
)