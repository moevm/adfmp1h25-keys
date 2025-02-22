package ru.etu.duplikeytor.presentation.navigation

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.etu.duplikeytor.presentation.navigation.model.ScreenType
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    // нигде не используется, но может понадобиться в будущем
    private val _currentScreen = MutableStateFlow(ScreenType.CREATE)

    fun onScreenChanged(screen: ScreenType) {
        _currentScreen.value = screen
    }

    // понадобится позже, пока заглушка
    fun onLifecycleEvent(event:  Lifecycle. Event) {
        when(event) {
            Lifecycle.Event.ON_START -> Log.d("Test", "MainVM ON_START")
            Lifecycle.Event.ON_STOP -> Log.d("Test", "MainVM ON_START")
            else -> Log.d("Test", "MainVM ${event}")
        }
    }
}