package ru.etu.duplikeytor.presentation.archive.keycard

import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig
import ru.etu.duplikeytor.presentation.shared.model.KeyType

internal data class KeyState(
    val id: Long,
    val name: String,
    val scale: Float,
    val imageUri: String?,
    val createdAt: String,
    val type: KeyType,
    val pins: String,
    val config: KeyConfig? = null,
)