package ru.etu.duplikeytor.presentation.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.presentation.archive.keycard.KeyCard
import ru.etu.duplikeytor.presentation.archive.keycard.KeyCardState
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState

@Composable
internal fun ArchiveScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    state: KeyArchiveState.KeysList,
    onClick: (KeyCardState) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPadding),
    ) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(16.dp),
            columns = GridCells.Adaptive(minSize = 150.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(state.keys.size) { index ->
                val keyCardState = state.keys[index]
                KeyCard(
                    state = keyCardState,
                    onClick = onClick,
                )
            }
        }
    }
}