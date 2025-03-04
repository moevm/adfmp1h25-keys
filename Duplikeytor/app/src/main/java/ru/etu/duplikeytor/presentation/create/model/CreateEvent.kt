package ru.etu.duplikeytor.presentation.create.model

import KeyChosenState

internal sealed interface CreateEvent {
    data class Next(val createStep: CreateStep) : CreateEvent
    data class KeyChoose(val chosenKey: KeyChosenState) : CreateEvent
    data class KeyScale(val scale: Float) : CreateEvent
    data object KeyScaled : CreateEvent
    data object KeyCreated : CreateEvent
    data object InterfaceVisibleChange : CreateEvent
    data class KeySave(val keyName: String) : CreateEvent
    data object Share : CreateEvent
}