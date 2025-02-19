package ru.etu.duplikeytor.presentation.navigation.model

data class NavigationBarState(
    val buttons: List<NavigationButtonState>,
    val initSelectedIndex: Int = 0,
)