package ru.etu.duplikeytor.presentation.create.model

import ru.etu.duplikeytor.presentation.create.model.chose.KeyChoseState

internal sealed interface CreateScreenState {
    val createStep: CreateStep

    data class Choose(
        override val createStep: CreateStep = CreateStep.CHOOSE,
        val keys: List<KeyChoseState>,
    ) : CreateScreenState
}