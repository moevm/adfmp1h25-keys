package ru.etu.duplikeytor.presentation.archive.keycard

import ru.etu.duplikeytor.presentation.shared.model.KeyType

internal data class KeyState(
    val id: Long,
    val name: String,
    val imageUri: String?,
    val createdAt: String,
    val type: KeyType,
    val pins: String,
)