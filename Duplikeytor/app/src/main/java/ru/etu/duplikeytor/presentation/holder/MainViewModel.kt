package ru.etu.duplikeytor.presentation.holder

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    init {
        observeStatusBar()
    }

    private fun observeStatusBar() {
        viewModelScope.launch {
            _currentScreen
            childScreen
             // on add or change currentScreen -> change statusbar if need
        }
    }

    // нигде не используется, но может понадобиться в будущем
    private val _currentScreen = MutableStateFlow(ScreenType.CREATE)

    fun onScreenChanged(screen: ScreenType) {
        _currentScreen.value = screen
        val currentStatusBar = childScreen.find {
            it.screenType == screen
        }?.statusBarState

        val currentNavigationBar = childScreen.find {
            it.screenType == screen
        }?.navigationBarState

        viewModelScope.launch {
            currentStatusBar?.let { statusBar ->
                _statusBarState.emit(statusBar)
            }
            currentNavigationBar?.let { navigationBar ->
                _navigationBarState.emit(navigationBar)
            }
        }
    }

    // понадобится позже, пока заглушка
    fun onLifecycleEvent(event:  Lifecycle. Event) {
        when(event) {
            Lifecycle.Event.ON_START -> Log.d("Test", "MainVM ON_START")
            Lifecycle.Event.ON_STOP -> Log.d("Test", "MainVM ON_START")
            else -> Log.d("Test", "MainVM ${event}")
        }
    }
    private val _statusBarState = MutableStateFlow<StatusBarState>(
        StatusBarState.Empty(false)
    )
    private val _navigationBarState = MutableStateFlow<NavigationBarState>(
        NavigationBarState(listOf())
    )

    val statusBarState: StateFlow<StatusBarState> = _statusBarState
    val navigationBarState: StateFlow<NavigationBarState> = _navigationBarState

    private val childScreen = mutableListOf<Screen>()

    fun registerScreen(screen: Screen) {
        childScreen.add(screen)
    }

}