package ru.etu.duplikeytor.presentation.navigation.model

import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState

data class NavigationBarState(
    val buttons: List<NavigationState>,
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