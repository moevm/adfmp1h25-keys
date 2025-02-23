package ru.etu.duplikeytor.domain.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType

class NavigationHandler(
    private val navController: NavHostController,
    private val onScreenChanged: (ScreenType) -> Unit,
) {
    private val _currentScreen = mutableStateOf(ScreenType.CREATE)
    val currentScreen: State<ScreenType> = _currentScreen
    val controller = navController

    init {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            ScreenType.entries.firstOrNull { it.route == destination.route }?.let { screen ->
                _currentScreen.value = screen
                onScreenChanged(screen)
            }
        }
    }

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
        onScreenChanged(screen)
    }
}