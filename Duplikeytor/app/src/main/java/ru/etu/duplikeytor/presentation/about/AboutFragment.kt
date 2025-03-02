package ru.etu.duplikeytor.presentation.about

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
internal fun AboutFragment(
    viewModel: AboutViewModel,
    contentPadding: PaddingValues,
    onBackClick: () -> Unit,
    onCreate: () -> Unit = {},
) {
    onCreate()

    val state by viewModel.state.collectAsState()

    BackHandler { onBackClick() }

    AboutScreen(
        contentPadding = contentPadding,
        state = state,
    )
}
