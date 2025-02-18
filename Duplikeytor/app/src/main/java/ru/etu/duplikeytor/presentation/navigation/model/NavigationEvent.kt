package ru.etu.duplikeytor.presentation.navigation.model

sealed class NavigationEvent {
    data object Click : NavigationEvent()
}
