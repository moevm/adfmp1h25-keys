package ru.etu.duplikeytor.presentation.create.model

import ru.etu.duplikeytor.presentation.create.model.chose.KeyChooseState

internal sealed interface CreateScreenState {
    val createStep: CreateStep

    data class Choose(
        override val createStep: CreateStep = CreateStep.CHOOSE,
        val keys: List<KeyChooseState>,
    ) : CreateScreenState

    data class Scale(
        override val createStep: CreateStep = CreateStep.SCALE,
        val key: KeyChooseState,
    ) : CreateScreenState
}