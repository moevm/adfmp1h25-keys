package ru.etu.duplikeytor.presentation.navigation.model

import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState

data class NavigationBarState(
    val buttons: List<Pair<ButtonState.Icon.Navigation, ScreenType>>,
    val initSelectedIndex: Int = 0,
) {
    companion object {
        fun build() = NavigationBarState(
            listOf(
                ButtonState.Icon.Navigation(
                    icon = R.drawable.ic_key_black,
                ) to ScreenType.CREATE,
                ButtonState.Icon.Navigation(
                    icon = R.drawable.ic_saved_black,
                ) to ScreenType.ARCHIVE,
                ButtonState.Icon.Navigation(
                    icon = R.drawable.ic_about_black,
                ) to ScreenType.ABOUT,
            ),
        )
    }
}