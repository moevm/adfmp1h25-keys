package ru.etu.duplikeytor.presentation.archive.model

internal sealed interface KeyArchiveState {
    data class KeysList(val keys: List<KeyState>, val title: String) : KeyArchiveState
    data class Key(val key: KeyState, val title: String) : KeyArchiveState
}