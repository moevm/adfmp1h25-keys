package ru.etu.duplikeytor.presentation.create.view.save

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig
import ru.etu.duplikeytor.presentation.create.view.util.Key
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton

@Composable
internal fun SaveScreen(
    modifier: Modifier = Modifier,
    state: CreateScreenState.Save,
    onEvent: (CreateEvent) -> Unit,
) {
    val textState = remember { mutableStateOf(state.keyTitle) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        KeyPicture(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .aspectRatio(0.8f),
        )
        KeyInformation(
            state = state,
            keyTitle = textState,
            onTitleChange = { title ->
                onEvent(CreateEvent.KeyTitleChange(title))
            }
        )
        ButtonRow(
            onEvent = onEvent,
        )
    }
}

@Composable
private fun KeyPicture(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Key(
            modifier = Modifier
                .aspectRatio(0.33f)
                .padding(vertical = 20.dp)
                .weight(1f),
            color = Color.Transparent,
            borderColor = Color.Transparent,
            pinsColor = MaterialTheme.colorScheme.background,
        )
        UiKitButton(
            modifier = Modifier
                .fillMaxWidth(),
            button = ButtonState.Icon.Default(
                icon = R.drawable.ic_add_photo_white,
            ),
            onClick = { },
        )
    }
}

@Composable
private fun KeyInformation(
    modifier: Modifier = Modifier,
    state: CreateScreenState.Save,
    keyTitle: MutableState<String>,
    onTitleChange: (String) -> Unit,
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.key.type.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.keyConfig.toTitle(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 20.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp)),
            value = keyTitle.value,
            onValueChange = {
                onTitleChange(it)
                keyTitle.value = it
            },
            label = { Text("Название ключа") },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            )
        )
    }
}

private fun KeyConfig.toTitle() =
    when(this) {
        is KeyConfig.Kwikset -> {
            this.pins.joinToString(separator = "–")
        }
        else -> "Unsupported type" // TODO
    }

@Composable
private fun ButtonRow(
    modifier: Modifier = Modifier,
    onEvent: (CreateEvent) -> Unit,
) {
    val context = LocalContext.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Text("Сохранить"),
            onClick = { onEvent(CreateEvent.KeySave) },
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(
                icon = R.drawable.ic_share_white,
            ),
            onClick = { onEvent(CreateEvent.Share(context)) },
        )
    }
}