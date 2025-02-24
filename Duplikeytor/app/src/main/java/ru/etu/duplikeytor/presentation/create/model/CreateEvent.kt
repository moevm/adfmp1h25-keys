package ru.etu.duplikeytor.presentation.create.model

import ru.etu.duplikeytor.presentation.create.model.chose.KeyChosenState

internal sealed interface CreateEvent {
    data class Next(val createStep: CreateStep)
    data class KeyChoose(val chosenKey: KeyChosenState)
    data class KeyScaled(val scale: Float)
}