package ru.etu.duplikeytor.presentation.archive.keycard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveEvent
import ru.etu.duplikeytor.presentation.archive.model.KeyArchiveState
import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig
import ru.etu.duplikeytor.presentation.create.view.util.Key
import ru.etu.duplikeytor.presentation.shared.model.KeyType
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton

private const val imageRatio = 3f / 2f

@Composable
internal fun KeyInfoScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    state: KeyArchiveState.Key,
    onEvent: (KeyArchiveEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPadding),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        KeyInformation(
            state = state.key,
        )
        ButtonRow(
            onEvent = onEvent,
            state = state.key,
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
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Добавлен",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = state.createdAt,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        KeyPicture(
            modifier = Modifier.aspectRatio(1f),
            state = state,
        )
        Text(
            modifier = modifier.fillMaxWidth(),
            text = state.type.toString(),
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
private fun KeyPicture(
    modifier: Modifier = Modifier,
    state: KeyState,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        if (state.imageUri.isNullOrEmpty() && state.config != null) {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val minSizeValue = minOf(screenWidth, screenHeight)/2
            Key(
                modifier = Modifier
                    .size(
                        width = minSizeValue/ KeyConfig.Kwikset.init.sizeRatio,
                        height = minSizeValue
                    )
                    .graphicsLayer {
                        scaleX = state.scale
                        scaleY = state.scale
                    }
                    .aspectRatio(0.285f),
                color = MaterialTheme.colorScheme.onBackground,
                borderColor = Color.Transparent,
                pinsColor = MaterialTheme.colorScheme.background,
                pins = state.pins.split("-").map { it.toInt() },
                keyConfig = state.config,
            )
        } else {
            AsyncImage(
                modifier = modifier.aspectRatio(imageRatio),
                model = state.imageUri,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ButtonRow(
    modifier: Modifier = Modifier,
    onEvent: (KeyArchiveEvent) -> Unit,
    state: KeyState,
) {
    val context = LocalContext.current
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
            onClick = { onEvent(KeyArchiveEvent.Delete(state)) },
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Text("Изменить"),
            onClick = { onEvent(KeyArchiveEvent.Edit(state)) },
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(
                icon = R.drawable.ic_share_white,
            ),
            onClick = { onEvent(KeyArchiveEvent.Share(context, state)) },
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
            key = KeyState(
                id = 0,
                name = "Key name",
                scale = 1f,
                imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
                createdAt = "10.10.2021 - 13:37",
                type = KeyType.KWIKSET,
                pins = "1-2-3-4-5",
            ),
            title = "Key title",
        ),
        onEvent = {},
    )
}