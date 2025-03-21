package ru.etu.duplikeytor.presentation.create.model

import KeyChosenState
import android.content.Context
import android.net.Uri

internal sealed interface CreateEvent {
    data class Next(val createStep: CreateStep) : CreateEvent
    data class KeyChoose(val chosenKey: KeyChosenState) : CreateEvent
    data class KeyScale(val scale: Float) : CreateEvent
    data object KeyScaled : CreateEvent
    data class KeyCreateChanged(val pin: Int, val deep: Int) : CreateEvent
    data object KeyCreated : CreateEvent
    data object InterfaceVisibleChange : CreateEvent
    data class KeyTitleChange(val title: String) : CreateEvent
    data object KeySave : CreateEvent
    data class Share(val context: Context) : CreateEvent
    data class SetKeyImage(val uri: Uri) : CreateEvent
    data object DeleteKeyPhoto : CreateEvent
}