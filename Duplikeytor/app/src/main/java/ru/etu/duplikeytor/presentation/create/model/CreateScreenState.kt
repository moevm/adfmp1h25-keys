package ru.etu.duplikeytor.presentation.create.model

import KeyChosenState
import android.net.Uri
import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig

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

    data class Create(
        override val createStep: CreateStep = CreateStep.CREATE,
        val key: KeyChosenState,
        val scale: Float,
        val keyConfig: KeyConfig,
    ) : CreateScreenState

    data class Save(
        override val createStep: CreateStep = CreateStep.SAVE,
        val key: KeyChosenState,
        val keyTitle: String,
        val scale: Float,
        val keyImageUri: Uri?,
        val keyConfig: KeyConfig,
    ) : CreateScreenState
}
