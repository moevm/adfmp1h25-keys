package ru.etu.duplikeytor.presentation.uiKit.button

sealed interface ButtonState {

    sealed interface Icon : ButtonState {
        val icon: Int

        data class Default(override val icon: Int) : Icon
        data class Warning(override val icon: Int) : Icon
    }
    data class Text(val text: String) : ButtonState
}