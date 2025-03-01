package ru.etu.duplikeytor.presentation.holder.model.status

sealed interface StatusBarState {

    val requiredDisplay: Boolean

    val onBackClick: (() -> Unit)?

    data class Title(
        val title: String,
        override val requiredDisplay: Boolean,
        override val onBackClick: (() -> Unit)? = null,
    ) : StatusBarState

    data class Empty(
        override val requiredDisplay: Boolean,
        override val onBackClick: (() -> Unit)? = null,
    ) : StatusBarState
}