package ru.etu.duplikeytor.presentation.archive.model

import ru.etu.duplikeytor.presentation.archive.keycard.KeyCardState

internal sealed interface KeyArchiveState {
    data class KeysList(val keys: List<KeyCardState>, val title: String) : KeyArchiveState
}