package ru.etu.duplikeytor.presentation.archive

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.etu.duplikeytor.presentation.archive.keycard.KeyInfoScreen
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveEvent
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState

@Composable
internal fun ArchiveFragment(
    viewModel: ArchiveViewModel,
    contentPadding: PaddingValues,
    onBackClick: () -> Boolean,
    onBackFailure: () -> Unit,
    onCreate: () -> Unit = {},
    onKeyEditIntent: (Long) -> Unit,
) {
    onCreate()

    val state by viewModel.state.collectAsState()

    BackHandler {
        if (!onBackClick()) {
            onBackFailure()
        }
    }

    when(state) {
        is KeyArchiveState.KeysList -> {
            ArchiveScreen(
                contentPadding = contentPadding,
                state = state as KeyArchiveState.KeysList,
                onClick = viewModel::onKeySelected,
            )
        }
        is KeyArchiveState.Key -> {
            KeyInfoScreen(
                contentPadding = contentPadding,
                state = state as KeyArchiveState.Key,
                onEvent = { event ->
                    when(event) {
                        is KeyArchiveEvent.Delete -> viewModel.onKeyDelete(event.key.id)
                        is KeyArchiveEvent.Edit -> onKeyEditIntent(event.key.id)
                        is KeyArchiveEvent.Share -> viewModel.onKeyShare(event.context, event.state)
                    }
                }
            )
        }
    }
}
