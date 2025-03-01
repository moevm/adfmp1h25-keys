package ru.etu.duplikeytor.presentation.create.model

import ru.etu.duplikeytor.presentation.create.model.choose.KeyChosenState

internal sealed interface CreateEvent {
    data class Next(val createStep: CreateStep) : CreateEvent
    data class KeyChoose(val chosenKey: KeyChosenState) : CreateEvent
    data class KeyScale(val scale: Float) : CreateEvent
    data object KeyScaled : CreateEvent
    data object KeyCreated : CreateEvent
    data class InterfaceVisibleChange(val isVisible: Boolean) : CreateEvent
}