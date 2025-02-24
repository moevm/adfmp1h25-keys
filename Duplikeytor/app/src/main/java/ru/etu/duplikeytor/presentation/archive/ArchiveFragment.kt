package ru.etu.duplikeytor.presentation.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.etu.duplikeytor.presentation.archive.keycard.KeyState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState

@Composable
internal fun ArchiveFragment(
    viewModel: ArchiveViewModel,
    onCreate: () -> Unit = {},
    contentPadding: PaddingValues,
) {
    onCreate()

    val state by viewModel.state.collectAsState()

    when(state) {
        is KeyArchiveState.KeysList -> {
            ArchiveScreen(
                contentPadding = contentPadding,
                state = state as KeyArchiveState.KeysList,
                onClick = viewModel::onKeySelected,
            )
        }
        is KeyArchiveState.Key -> {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black),
            )
        }
    }
}
