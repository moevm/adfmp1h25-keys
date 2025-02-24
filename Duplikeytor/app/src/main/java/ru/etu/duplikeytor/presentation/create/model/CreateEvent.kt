package ru.etu.duplikeytor.presentation.create.model

import ru.etu.duplikeytor.presentation.create.model.chose.KeyChoseState

internal sealed interface CreateEvent {
    data class Next(val createStep: CreateStep)
    data class KeyChoose(val chosenKey: KeyChoseState)
}