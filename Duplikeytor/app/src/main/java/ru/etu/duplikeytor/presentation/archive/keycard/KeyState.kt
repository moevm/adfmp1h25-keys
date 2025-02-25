package ru.etu.duplikeytor.presentation.archive.keycard

internal data class KeyState(
    val name: String,
    val imageUri: String?,
    val createdAt: String,
    val type: String,
    val pins: String,
)