package ru.etu.duplikeytor.presentation.archive.model

import ru.etu.duplikeytor.presentation.archive.keycard.KeyState

internal sealed interface KeyArchiveState {
    data class KeysList(val keys: List<KeyState>, val title: String) : KeyArchiveState
    data class Key(val key: KeyState, val title: String) : KeyArchiveState
}