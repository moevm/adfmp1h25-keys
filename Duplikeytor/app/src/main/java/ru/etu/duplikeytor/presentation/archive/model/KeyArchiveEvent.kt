package ru.etu.duplikeytor.presentation.archive.model

import android.content.Context

internal sealed interface KeyArchiveEvent {
    data class Delete(val key: KeyState) : KeyArchiveEvent
    data class Edit(val key: KeyState) : KeyArchiveEvent
    data class Share(val context: Context, val state: KeyState) : KeyArchiveEvent
}