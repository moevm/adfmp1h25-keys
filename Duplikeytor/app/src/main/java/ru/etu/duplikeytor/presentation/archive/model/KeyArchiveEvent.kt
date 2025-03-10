package ru.etu.duplikeytor.presentation.archive.model

import ru.etu.duplikeytor.presentation.archive.keycard.KeyState

internal sealed interface KeyArchiveEvent {
    data class Delete(val key: KeyState) : KeyArchiveEvent
    data class Edit(val key: KeyState) : KeyArchiveEvent
    data class Repost(val key: KeyState) : KeyArchiveEvent
}