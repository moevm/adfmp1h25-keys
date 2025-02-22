package ru.etu.duplikeytor.presentation.create

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable

@Composable
internal fun CreateFragment(
    viewModel: CreateViewModel,
    contentPadding: PaddingValues,
) {
    CreateScreen(
        contentPadding = contentPadding,
    )
}