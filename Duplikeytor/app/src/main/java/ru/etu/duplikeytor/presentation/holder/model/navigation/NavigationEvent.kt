package ru.etu.duplikeytor.presentation.holder.model.navigation

internal sealed interface NavigationEvent {
    val targetRoute: ScreenType

    data class Click(override val targetRoute: ScreenType) : NavigationEvent
}
