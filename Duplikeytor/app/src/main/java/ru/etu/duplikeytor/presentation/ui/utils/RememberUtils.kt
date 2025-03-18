package ru.etu.duplikeytor.presentation.ui.utils

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T : Any> snapshotStateListSaver() = listSaver(
    save = { state -> state.toList() },
    restore = { restored -> SnapshotStateList<T>().apply { addAll(restored) } }
)
