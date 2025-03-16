package ru.etu.duplikeytor.presentation.holder.model

import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType

internal sealed  interface AppEvent {
    val eventScreenResolver: ScreenType

    data object Main : AppEvent {
        override val eventScreenResolver: ScreenType
            get() = ScreenType.main
    }

    sealed interface Create : AppEvent {
        override val eventScreenResolver: ScreenType
            get() = ScreenType.CREATE

        data class KeyEdit(val id: Long): Create
    }

    sealed interface Archive : AppEvent {
        override val eventScreenResolver: ScreenType
            get() = ScreenType.ARCHIVE

        data class KeyAdded(val id: Long) : Archive
    }

    sealed interface About : AppEvent
}