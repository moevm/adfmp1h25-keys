package ru.etu.duplikeytor.presentation.holder

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.flow.StateFlow
import ru.etu.duplikeytor.presentation.holder.navigation.NavigationHandler
import ru.etu.duplikeytor.presentation.about.AboutFragment
import ru.etu.duplikeytor.presentation.about.AboutViewModel
import ru.etu.duplikeytor.presentation.archive.ArchiveFragment
import ru.etu.duplikeytor.presentation.archive.ArchiveViewModel
import ru.etu.duplikeytor.presentation.create.CreateFragment
import ru.etu.duplikeytor.presentation.create.CreateViewModel
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.holder.view.navbar.NavigationBar
import ru.etu.duplikeytor.presentation.holder.view.statusbar.StatusBar
import ru.etu.duplikeytor.presentation.shared.model.Screen
import ru.etu.duplikeytor.presentation.ui.utils.toDp

@Composable
internal fun MainScreen(
    onScreenChanged: (ScreenType) -> Unit,
    onLifecycleEvent: (Lifecycle. Event) -> Unit,
    registerScreen: (Screen) -> Unit,
    statusBarStateFlow: StateFlow<StatusBarState>,
    navigationBarStateFlow: StateFlow<NavigationBarState>,
) {
    val navController = rememberNavController()
    val navigationHandler = remember {
        NavigationHandler(
            navController = navController,
            onScreenChanged = onScreenChanged,
        )
    }
    onScreenChanged(ScreenType.main)
    val lifecycleOwner = LocalLifecycleOwner.current

    val navigationPaddingPx = remember { mutableIntStateOf(0) }
    val statusBarPaddingPx = remember { mutableIntStateOf(0) }

    val statusBarState by statusBarStateFlow.collectAsState()
    val navigationBarState by navigationBarStateFlow.collectAsState()

    Scaffold(
        topBar = {
            StatusBar(
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    statusBarPaddingPx.intValue = layoutCoordinates.size.height
                },
                state = statusBarState,
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                    navigationPaddingPx.intValue = layoutCoordinates.size.height
                },
                currentScreen = { navigationHandler.currentScreen.value },
                state = navigationBarState,
                onClick = { targetRoute ->
                    navigationHandler.navigateToScreen(targetRoute)
                },
            )
        }
    ) { innerPadding -> innerPadding.toString()
        val createViewModel: CreateViewModel = hiltViewModel<CreateViewModel>().also {
            registerScreen(it)
        }
        val archiveViewModel: ArchiveViewModel = hiltViewModel<ArchiveViewModel>().also {
            registerScreen(it)
        }
        val aboutViewModel: AboutViewModel = hiltViewModel<AboutViewModel>().also {
            registerScreen(it)
        }
        NavHost(
            modifier = Modifier,
            navController = navigationHandler.controller,
            startDestination = ScreenType.main.route,
        ) {
            composable(ScreenType.CREATE.route) {
                CreateFragment(
                    viewModel = createViewModel,
                    contentPadding = PaddingValues(
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
            composable(ScreenType.ARCHIVE.route) {
                ArchiveFragment(
                    viewModel = archiveViewModel,
                    contentPadding = PaddingValues(
                        bottom = navigationPaddingPx.intValue.toDp(),
                    )
                )
            }
            composable(ScreenType.ABOUT.route) {
                AboutFragment(
                    viewModel = aboutViewModel,
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
