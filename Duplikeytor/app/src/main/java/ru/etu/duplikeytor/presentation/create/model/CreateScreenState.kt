package ru.etu.duplikeytor.presentation.create.model

import ru.etu.duplikeytor.presentation.create.model.choose.KeyChosenState

internal sealed interface CreateScreenState {
    val createStep: CreateStep

    data class Choose(
        override val createStep: CreateStep = CreateStep.CHOOSE,
        val keys: List<KeyChosenState> = listOf(),
    ) : CreateScreenState

    data class Scale(
        override val createStep: CreateStep = CreateStep.SCALE,
        val key: KeyChosenState,
        val initialScale: Float,
    ) : CreateScreenState

    data class Change(
        override val createStep: CreateStep = CreateStep.CHANGE,
        // TODO
    ) : CreateScreenState
}