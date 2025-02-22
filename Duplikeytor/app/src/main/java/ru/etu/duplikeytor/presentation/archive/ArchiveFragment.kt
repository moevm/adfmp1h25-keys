package ru.etu.duplikeytor.presentation.archive

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
internal fun ArchiveFragment(
    viewModel: ArchiveViewModel,
    contentPadding: PaddingValues,
) {
    ArchiveScreen(
        contentPadding = contentPadding,
    )
}