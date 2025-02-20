package ru.etu.duplikeytor.presentation.uiKit.button

sealed interface ButtonState {
    val color: Long

    data class Icon(override val color: Long, val icon: Int) : ButtonState
    data class Text(override val color: Long, val text: String) : ButtonState
}