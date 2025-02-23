package ru.etu.duplikeytor.presentation.archive

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import ru.etu.duplikeytor.presentation.archive.keycard.KeyCardState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState

@Composable
internal fun ArchiveFragment(
    viewModel: ArchiveViewModel,
    onCreate: () -> Unit = {},
    contentPadding: PaddingValues,
) {
    onCreate()

    // Мусорные данные
    val keyArchiveState = KeyArchiveState.KeysList(
        keys = listOf(
            KeyCardState(
                name = "Key 1",
                imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
                createdAt = "10.10.2021 - 13:37",
            ),
            KeyCardState(
                name = "Key 2",
                imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
                createdAt = "10.11.2021 - 13:37",
            ),
            KeyCardState(
                name = "Key 3",
                imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
                createdAt = "10.12.2021 - 13:37",
            ),
        ),
        title = "Мои ключи",
    )
    viewModel.changeStatusBarTitle(keyArchiveState.title)

    ArchiveScreen(
        contentPadding = contentPadding,
        state = keyArchiveState,
        onClick = { keyCardState ->
            // TODO
        }
    )
}
