package ru.etu.duplikeytor.presentation.holder.model.navigation

import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState

data class NavigationBarState(
    val buttons: List<NavigationState>,
    val requiredDisplay: Boolean = true,
) {
    companion object {
        fun build() = NavigationBarState(
            listOf(
                NavigationState(
                    button = ButtonState.Icon.Navigation(R.drawable.ic_key_black),
                    screen = ScreenType.CREATE,
                ),
                NavigationState(
                    button = ButtonState.Icon.Navigation(R.drawable.ic_saved_black),
                    screen = ScreenType.ARCHIVE,
                ),
                NavigationState(
                    button = ButtonState.Icon.Navigation(R.drawable.ic_about_black),
                    screen = ScreenType.ABOUT,
                )
            )
        )
    }
}