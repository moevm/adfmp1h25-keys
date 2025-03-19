package ru.etu.duplikeytor.presentation.create.model.config

internal sealed interface KeyConfig {
    val pins: List<Int>
    val maxDepth: Int
    val totalPins: Int
    val sizeRatio: Float

    data class Kwikset(
        override val pins: List<Int>,
    ) : KeyConfig {
        override val maxDepth: Int = 7
        override val totalPins: Int = 5
        override val sizeRatio: Float = 3.4239F

        companion object {
            val init = Kwikset(listOf(1, 1, 1, 1, 1))
        }
    }

    data class Schlage(
        override val pins: List<Int>,
    ) : KeyConfig {
        override val maxDepth: Int = 10
        override val totalPins: Int = 6
        override val sizeRatio: Float = 3.4519F

        companion object {
            val init = Schlage(listOf(1, 1, 1, 1, 1, 1))
        }
    }
}