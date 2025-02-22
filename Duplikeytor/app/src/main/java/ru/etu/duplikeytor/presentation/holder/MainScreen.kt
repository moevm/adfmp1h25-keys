package ru.etu.duplikeytor.presentation.holder

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.etu.duplikeytor.domain.navigation.NavigationHandler
import ru.etu.duplikeytor.presentation.navigation.model.NavigationBarState
import ru.etu.duplikeytor.presentation.navigation.model.ScreenType
import ru.etu.duplikeytor.presentation.navigation.view.NavigationBar
import ru.etu.duplikeytor.presentation.screens.AboutScreen
import ru.etu.duplikeytor.presentation.screens.ArchiveScreen
import ru.etu.duplikeytor.presentation.screens.CreateScreen
import ru.etu.duplikeytor.presentation.ui.utils.toDp

@Composable
fun MainScreen(
    onScreenChanged: (ScreenType) -> Unit,
) {
    val navController = rememberNavController()
    val navigationHandler = remember {
        NavigationHandler(
            navController = navController,
            onScreenChanged = onScreenChanged,
        )
    }

    val navigationPaddingPx = remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    navigationPaddingPx.intValue = layoutCoordinates.size.height
                },
                currentScreen = { navigationHandler.currentScreen.value },
                state = NavigationBarState.build(),
                onClick = { targetRoute ->
                    navigationHandler.navigateToScreen(targetRoute)
                },
            )
        }
    ) { innerPadding -> innerPadding.toString()
        NavHost(
            modifier = Modifier,
            navController = navigationHandler.controller,
            startDestination = ScreenType.main.route,
        ) {
            composable(ScreenType.CREATE.route) {
                CreateScreen(
                    contentPadding = PaddingValues(
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
            composable(ScreenType.ARCHIVE.route) {
                ArchiveScreen(
                    contentPadding = PaddingValues(
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
            composable(ScreenType.ABOUT.route) {
                AboutScreen(
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
        }
    }
}
