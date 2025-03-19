package ru.etu.duplikeytor.presentation.holder

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.holder.model.AppEvent
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
    private val _currentScreenType = MutableStateFlow(ScreenType.main)
    private val _currentScreen = MutableStateFlow<Screen?>(null)

    private val _eventResolver = MutableSharedFlow<AppEvent>()

    val statusBarState: StateFlow<StatusBarState> = _statusBarState
    val navigationBarState: StateFlow<NavigationBarState> = _navigationBarState

    private val childScreen = mutableMapOf<ScreenType, Screen>()

    internal fun registerScreen(screen: Screen) {
        childScreen[screen.screenType] = screen
    }

    init {
        observeStatusBar()
        observeNavigationBar()
        observeEvent()
    }

    private fun observeStatusBar() {
        viewModelScope.launch {
            _currentScreen.collect { screen ->
                Log.e("Fix", "MainVM observeStatusBar _currentScreen collect screen ${screen}")
                screen?.statusBarState?.collect { statusBar ->
                    Log.e("Fix", "MainVM observeStatusBar _statusBarState emit statusBar ${statusBar}")
                    _statusBarState.emit(statusBar)
                }
            }
        }
    }

    private fun observeNavigationBar() {
        viewModelScope.launch {
            _currentScreen.collect { screen ->
                screen?.navigationBarState?.collect { navigationBar ->
                    _navigationBarState.emit(navigationBar)
                }
            }
        }
    }

    private fun observeEvent() {
        viewModelScope.launch {
            _eventResolver.collect { event ->
                if (event == AppEvent.Main) return@collect
                childScreen[event.eventScreenResolver]?.notifyResolveEvent(event)
            }
        }
    }

    internal fun processAppEvent(event: AppEvent) {
        viewModelScope.launch {
            _eventResolver.emit(event)
        }
    }

    fun onBackClick() = _currentScreen.value?.onBackClick() ?: false

    fun onScreenChanged(screenType: ScreenType) {
        _currentScreenType.value = screenType
        _currentScreen.value = childScreen[screenType]

        val currentStatusBar = _currentScreen.value?.statusBarState
        val currentNavigationBar = _currentScreen.value?.navigationBarState

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