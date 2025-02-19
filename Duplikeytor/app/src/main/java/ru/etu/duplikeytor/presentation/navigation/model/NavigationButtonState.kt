package ru.etu.duplikeytor.presentation.navigation.model

data class NavigationButtonState(
    val iconRes: Int,
    val onClick: (NavigationEvent) -> Unit,
)
