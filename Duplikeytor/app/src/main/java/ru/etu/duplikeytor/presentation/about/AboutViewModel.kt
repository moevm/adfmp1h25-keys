package ru.etu.duplikeytor.presentation.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.about.model.AboutScreenState
import ru.etu.duplikeytor.presentation.about.model.DepartmentState
import ru.etu.duplikeytor.presentation.about.model.DeveloperState
import ru.etu.duplikeytor.presentation.holder.model.AppEvent
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

internal class AboutViewModel @Inject constructor() : ViewModel(), Screen {

    override var statusBarState = MutableStateFlow<StatusBarState>(
        StatusBarState.Empty(false)
    )
    override var navigationBarState = MutableStateFlow(
        NavigationBarState.build()
    )
    override val screenType: ScreenType = ScreenType.ABOUT

    override fun onBackClick(): Boolean = false

    override fun notifyResolveEvent(event: AppEvent) {}

    private val _state = MutableStateFlow(AboutScreenState(
        developers = getDevelopers(),
        department = getDepartment(),
    ))

    private val emoji = listOf(
        "(◕◡◕)", "(◑◡◑)", "(◔◡◔)", "(○◡○)", "(◔◡◔)", "(◑◡◑)", "(◕◡◕)", "(●◡●)",
    )

    val state: StateFlow<AboutScreenState> = _state

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            var secondsLeft = 0
            while (true) {
                changeDepartment(secondsLeft)
                delay(500L)
                secondsLeft++
                if (secondsLeft == Int.MAX_VALUE) secondsLeft = 0
            }
        }
    }

    private fun changeDepartment(secondsLeft: Int) {
        _state.value = _state.value.copy(
            department = DepartmentState(
                name = "ETU MOEVM\t" + emoji[secondsLeft % emoji.size],
                uri = _state.value .department.uri,
            )
        )
    }

    private fun getDevelopers() = listOf(
        DeveloperState(
            "Ajems",
            "https://github.com/Ajems",
            "https://avatars.githubusercontent.com/u/70469206?v=4"
        ),
        DeveloperState(
            "1that",
            "https://github.com/1that",
            "https://avatars.githubusercontent.com/u/90708652?v=4"
        ),
        DeveloperState(
            "D1mitrii",
            "https://github.com/D1mitrii",
            "https://avatars.githubusercontent.com/u/90792387?v=4"
        )
    )

    private fun getDepartment() = DepartmentState(
        name = "ETU MOEVM",
        uri = "https://se.moevm.info/doku.php/start",
    )
}
