package ru.etu.duplikeytor.presentation.holder.model.status

sealed interface StatusBarState {

    val requiredDisplay: Boolean

    data class Title(
        val title: String,
        override val requiredDisplay: Boolean
    ) : StatusBarState

    data class Empty(
        override val requiredDisplay: Boolean
    ) : StatusBarState
}