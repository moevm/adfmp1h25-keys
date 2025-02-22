package ru.etu.duplikeytor.presentation.navigation.model

import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState

data class NavigationBarState(
    val buttons: List<NavigationState>,
    val initSelectedIndex: Int = 0,
) {
    companion object {
        fun build() = NavigationBarState(
            listOf(
                NavigationState(
                    ButtonState.Icon.Navigation(
                        icon = R.drawable.ic_key_black,
                    ),
                    ScreenType.CREATE,
                ),
                NavigationState(
                    ButtonState.Icon.Navigation(
                        icon = R.drawable.ic_saved_black,
                    ),
                    ScreenType.ARCHIVE,
                ),
                NavigationState(
                    ButtonState.Icon.Navigation(
                        icon = R.drawable.ic_about_black,
                    ),
                    ScreenType.ABOUT,
                )
            )
        )
    }
}