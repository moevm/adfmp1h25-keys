package ru.etu.duplikeytor.presentation.archive.keycard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton

@Composable
internal fun KeyInfoScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    state: KeyArchiveState.Key,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        KeyInformation(
            state = state.keyState,
        )
        ButtonRow(
            onClickTrash = {},
            onClickEdit = {},
            onClickShare = {},
        )
    }
}

@Composable
private fun KeyInformation(
    modifier: Modifier = Modifier,
    state: KeyState,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "Добавлен\n" + state.createdAt,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 26.sp),
        )
        AsyncImage(
            modifier = modifier.aspectRatio(1.5f),
            model = state.imageUri,
            contentDescription = null,
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            text = state.type,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            text = state.pins,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ButtonRow(
    modifier: Modifier = Modifier,
    onClickTrash: () -> Unit,
    onClickEdit: () -> Unit,
    onClickShare: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Warning(
                icon = R.drawable.ic_trash_black,
            ),
            onClick = onClickTrash
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Text("Изменить"),
            onClick = onClickEdit
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(
                icon = R.drawable.ic_share_white,
            ),
            onClick = onClickShare
        )
    }
}

@Preview
@Composable
private fun KeyCardInfoPreview() {
    KeyInfoScreen(
        modifier = Modifier,
        contentPadding = PaddingValues(top = 60.dp, bottom = 60.dp),
        state = KeyArchiveState.Key(
            keyState = KeyState(
                name = "Key name",
                imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                createdAt = "10.10.2021 - 13:37",
                type = "Kwikset",
                pins = "1-2-3-4-5",
            ),
            title = "Key title",
        ),
    )
}