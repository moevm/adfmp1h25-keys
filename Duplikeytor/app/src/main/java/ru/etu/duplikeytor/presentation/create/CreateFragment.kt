package ru.etu.duplikeytor.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.chose.KeyChoseState
import ru.etu.duplikeytor.presentation.create.view.choose.ChoseScreen

@Composable
internal fun CreateFragment(
    viewModel: CreateViewModel,
    onCreate: () -> Unit = {},
    contentPadding: PaddingValues,
) {
    onCreate()

    val state = CreateScreenState.Choose(
        keys = listOf(
            KeyChoseState(
                imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
                title = "Kwikset"
            ),
            KeyChoseState(
                imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                title = "SCHLAGE"
            ),
            KeyChoseState(
                imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
                title = "Дима"
            ),
            KeyChoseState(
                imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
                title = "Kwikset."
            ),
            KeyChoseState(
                imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                title = "SCHLAGE."
            ),
            KeyChoseState(
                imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
                title = "Дима."
            ),
        ),
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPadding)
    ) {
        ChoseScreen(
            modifier = Modifier,
            state = state,
            onEvent = {} // TODO next issue
        )
    }
}