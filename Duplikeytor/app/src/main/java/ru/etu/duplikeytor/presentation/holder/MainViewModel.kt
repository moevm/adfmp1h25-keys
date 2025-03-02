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

    private val _statusBarState = MutableStateFlow<StatusBarState>(
        StatusBarState.Empty(false)
    )
    private val _navigationBarState = MutableStateFlow(
        NavigationBarState(listOf())
    )
    private val _currentScreenType = MutableStateFlow(ScreenType.CREATE)
    private val _currentScreenImpl = MutableStateFlow<Screen?>(null)

    val statusBarState: StateFlow<StatusBarState> = _statusBarState
    val navigationBarState: StateFlow<NavigationBarState> = _navigationBarState

    private val childScreenImpl = mutableMapOf<ScreenType, Screen>()

    fun registerScreen(screen: Screen) {
        childScreenImpl[screen.screenType] = screen
    }

    init {
        observeStatusBar()
        observeNavigationBar()
    }

    private fun observeStatusBar() {
        viewModelScope.launch {
            _currentScreenImpl.collect { screen ->
                screen?.statusBarState?.collect { statusBar ->
                    _statusBarState.emit(statusBar)
                }
            }
        }
    }

    private fun observeNavigationBar() {
        viewModelScope.launch {
            _currentScreenImpl.collect { screen ->
                screen?.navigationBarState?.collect { navigationBar ->
                    _navigationBarState.emit(navigationBar)
                }
            }
        }
    }

    fun onBackClick() = _currentScreenImpl.value?.onBackClick() ?: false

    fun onScreenChanged(screenType: ScreenType) {
        _currentScreenType.value = screenType
        _currentScreenImpl.value = childScreenImpl[screenType]

        val currentStatusBar = _currentScreenImpl.value?.statusBarState
        val currentNavigationBar = _currentScreenImpl.value?.navigationBarState

        viewModelScope.launch {
            currentStatusBar?.collect { statusBar ->
                _statusBarState.emit(statusBar)
            }
            currentNavigationBar?.collect { navigationBar ->
                _navigationBarState.emit(navigationBar)
            }
        }
    }

    fun onLifecycleEvent(event:  Lifecycle. Event) {
        when(event) {
            Lifecycle.Event.ON_START -> Log.d("Test", "MainVM ON_START")
            Lifecycle.Event.ON_STOP -> Log.d("Test", "MainVM ON_START")
            else -> Log.d("Test", "MainVM ${event}")
        }
    }
}