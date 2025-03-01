package ru.etu.duplikeytor.presentation.ui.uiKit.button

sealed interface ButtonState {

    sealed interface Icon : ButtonState {
        val icon: Int

        data class Default(override val icon: Int) : Icon
        data class Warning(override val icon: Int) : Icon
        data class Action(override val icon: Int) : Icon
        data class Navigation(override val icon: Int, val isSelected: Boolean = false) : Icon
    }
    data class Text(val text: String) : ButtonState
}