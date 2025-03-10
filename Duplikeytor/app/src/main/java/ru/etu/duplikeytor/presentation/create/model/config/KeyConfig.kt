package ru.etu.duplikeytor.presentation.create.model.config

internal sealed interface KeyConfig {
    val pins: List<Int>
    data class Kwikset(
        override val pins: List<Int>,
    ) : KeyConfig {
        companion object {
            val init = Kwikset(listOf(0, 0, 0, 0, 0))
        }
    }

    data class Schlage(
        override val pins: List<Int>,
    ) : KeyConfig // TODO
}