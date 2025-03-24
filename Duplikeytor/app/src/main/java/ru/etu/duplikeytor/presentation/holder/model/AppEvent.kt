package ru.etu.duplikeytor.presentation.holder.model

import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType

internal sealed interface AppEvent {
    val eventScreenResolver: ScreenType

    data object Main : AppEvent {
        override val eventScreenResolver: ScreenType
            get() = ScreenType.main
    }

    sealed interface Create : AppEvent {
        override val eventScreenResolver: ScreenType
            get() = ScreenType.CREATE
    }

    sealed interface Archive : AppEvent {
        override val eventScreenResolver: ScreenType
            get() = ScreenType.ARCHIVE

        data class KeySaved(val id: Long) : Archive
    }
}