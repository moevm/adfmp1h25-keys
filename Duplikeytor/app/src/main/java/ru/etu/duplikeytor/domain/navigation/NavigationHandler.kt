package ru.etu.duplikeytor.domain.navigation

import androidx.navigation.NavController
import ru.etu.duplikeytor.presentation.navigation.model.ScreenType

class NavigationHandler(
    private val navController: NavController,
) {
    fun navigateToScreen(screen: ScreenType) {
        val currentDestination = navController.currentDestination?.route
        if (currentDestination == screen.route) return

        navController.navigate(screen.route) {
            popUpTo(ScreenType.main.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}