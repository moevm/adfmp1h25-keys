package ru.etu.duplikeytor.presentation.create.view.save

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig
import ru.etu.duplikeytor.presentation.create.view.util.Key
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton
import ru.etu.duplikeytor.presentation.ui.uiKit.dialog.ApproveDialog

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
            state = state,
            onEvent = onEvent,
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
    state: CreateScreenState.Save,
    onEvent: (CreateEvent) -> Unit,
) {
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let { onEvent(CreateEvent.SetKeyImage(it)) }
    }
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.surface),
    ) {
        val isError = remember { mutableStateOf(false) }
        if (state.keyImageUri != null && !isError.value) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f),
                model = state.keyImageUri,
                contentDescription = null,
                onError = { isError.value = true },
                contentScale = ContentScale.Crop,
            )
        } else {
            Key(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 80.dp)
                    .align(Alignment.Center)
                    .aspectRatio(1 / state.keyConfig.sizeRatio)
                    .zIndex(0f),
                keyConfig = state.keyConfig,
                color = MaterialTheme.colorScheme.onBackground,
                borderColor = Color.Transparent,
                pinsColor = MaterialTheme.colorScheme.surface,
                pins = state.keyConfig.pins,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(1f),
        ) {
            UiKitButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, bottom = 10.dp, end = 10.dp),
                button = ButtonState.Icon.Default(
                    icon = R.drawable.ic_add_photo_white,
                ),
                onClick = {
                    pickMedia.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                    )
                    isError.value = false
                },
            )
            AnimatedVisibility(
                visible = state.keyImageUri != null && !isError.value
            ) {
                val openDialog = remember { mutableStateOf(false) }
                UiKitButton(
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 10.dp)
                        .width(64.dp),
                    button = ButtonState.Icon.Warning(
                        icon = R.drawable.ic_trash_black,
                    ),
                    onClick = {
                        openDialog.value = true
                    },
                )
                if (openDialog.value) {
                    ApproveDialog(
                        title = "Удаление фотографии",
                        contentText = "Вы подтверждаете удаление фотографии? " +
                                "После удаление восстановление фотографии невозможно",
                        onDismissRequest = { isApprove ->
                            if (isApprove) {
                                onEvent(CreateEvent.DeleteKeyPhoto)
                            }
                            openDialog.value = false
                        }
                    )
                }
            }
        }
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

private fun KeyConfig.toTitle() = this.pins.joinToString(separator = "–")

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