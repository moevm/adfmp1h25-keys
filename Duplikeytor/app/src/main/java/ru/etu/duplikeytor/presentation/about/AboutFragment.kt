package ru.etu.duplikeytor.presentation.about

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun AboutFragment(
    viewModel: AboutViewModel,
    contentPadding: PaddingValues,
) {

    val state by viewModel.state.collectAsState()

    AboutScreen(
        contentPadding = contentPadding,
        state = state,
    )
}
