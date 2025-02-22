package ru.etu.duplikeytor.presentation.holder

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
}