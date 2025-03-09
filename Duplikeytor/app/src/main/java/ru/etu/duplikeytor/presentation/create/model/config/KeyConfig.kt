package ru.etu.duplikeytor.presentation.create.model.config

internal sealed interface KeyConfig {
    data class Kwikset(
        val pins: List<Int>,
    ) : KeyConfig {
        companion object {
            val init = Kwikset(listOf(0, 0, 0, 0, 0))
        }
    }

    data object Schlage : KeyConfig // TODO
}