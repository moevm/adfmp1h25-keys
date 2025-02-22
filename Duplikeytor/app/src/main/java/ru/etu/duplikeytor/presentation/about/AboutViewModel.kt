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
import javax.inject.Inject

internal class AboutViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AboutScreenState(
        developers = listOf(
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
        ),
        department = DepartmentState(
            name = "ETU MOEVM",
            uri = "https://se.moevm.info/doku.php/start",
        )
    ))

    val state: StateFlow<AboutScreenState> = _state

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            var secondsLeft = 0
            while (true) {
                changeDepartment(secondsLeft)
                delay(1000L)
                secondsLeft++
            }
        }
    }


    private fun changeDepartment(secondsLeft: Int) {
        _state.value = _state.value.copy(
            department = DepartmentState(
                name = "ETU MOEVM ${secondsLeft}",
                uri = "https://se.moevm.info/doku.php/start",
            )
        )
    }
}