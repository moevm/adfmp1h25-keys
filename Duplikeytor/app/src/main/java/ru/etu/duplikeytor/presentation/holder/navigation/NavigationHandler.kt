package ru.etu.duplikeytor.presentation.holder.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType

class NavigationHandler(
    private val navController: NavHostController,
    private val onScreenChanged: (ScreenType) -> Unit,
) {
    private val _currentScreen = mutableStateOf(ScreenType.main)
    private val _previousScreen = mutableStateOf(ScreenType.main)
    val currentScreen: State<ScreenType> = _currentScreen
    val previousScreen: State<ScreenType> = _previousScreen

    val controller = navController

    init {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            ScreenType.entries.firstOrNull { it.route == destination.route }?.let { screen ->
                _previousScreen.value = _currentScreen.value
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
    }

    fun navigateBack(exit: () -> Unit) {
        val current = currentScreen.value
        if (current != ScreenType.main) {
            navController.navigate(ScreenType.main.route) {
                popUpTo(ScreenType.main.route) {
                    inclusive = true
                }
            }
        } else {
            exit()
        }
    }
}