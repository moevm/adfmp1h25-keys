package ru.etu.duplikeytor.presentation.archive

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
internal fun ArchiveFragment(
    viewModel: ArchiveViewModel,
    onCreate: () -> Unit = {},
    contentPadding: PaddingValues,
) {
    onCreate()
    ArchiveScreen(
        contentPadding = contentPadding,
    )
}