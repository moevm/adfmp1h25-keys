package ru.etu.duplikeytor.presentation.holder.model.navigation

sealed class NavigationEvent {
    data object Click : NavigationEvent()
}
