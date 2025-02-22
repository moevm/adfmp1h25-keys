package ru.etu.duplikeytor.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.etu.duplikeytor.domain.navigation.NavigationHandler
import ru.etu.duplikeytor.presentation.about.AboutFragment
import ru.etu.duplikeytor.presentation.archive.ArchiveFragment
import ru.etu.duplikeytor.presentation.create.CreateFragment
import ru.etu.duplikeytor.presentation.navigation.model.NavigationBarState
import ru.etu.duplikeytor.presentation.navigation.model.ScreenType
import ru.etu.duplikeytor.presentation.navigation.navbar.NavigationBar
import ru.etu.duplikeytor.presentation.ui.utils.toDp

@Composable
fun MainScreen(
    onScreenChanged: (ScreenType) -> Unit,
    onLifecycleEvent: (Lifecycle. Event) -> Unit,
) {
    val navController = rememberNavController()
    val navigationHandler = remember {
        NavigationHandler(
            navController = navController,
            onScreenChanged = onScreenChanged,
        )
    }
    val lifecycleOwner = LocalLifecycleOwner.current

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
                CreateFragment(
                    viewModel = hiltViewModel(),
                    contentPadding = PaddingValues(
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
            composable(ScreenType.ARCHIVE.route) {
                ArchiveFragment(
                    viewModel = hiltViewModel(),
                    contentPadding = PaddingValues(
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
            composable(ScreenType.ABOUT.route) {
                AboutFragment(
                    viewModel = hiltViewModel(),
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            onLifecycleEvent(event)
        })
    }
}
