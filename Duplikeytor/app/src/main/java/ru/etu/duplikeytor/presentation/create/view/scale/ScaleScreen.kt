package ru.etu.duplikeytor.presentation.create.view.scale

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.choose.KeyType
import ru.etu.duplikeytor.presentation.create.view.util.Key
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton

@Composable
internal fun ScaleScreen(
    modifier: Modifier = Modifier,
    state: CreateScreenState.Scale,
    onEvent: (CreateEvent) -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        text = stringResource(R.string.description_scale_key),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        KeyScale(
            modifier = Modifier.fillMaxWidth().weight(1f),
            state = state,
            onKeyScale = { scale ->
                onEvent(CreateEvent.KeyScale(scale))
            }
        )

        Spacer(Modifier.height(10.dp))

        UiKitButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            button = ButtonState.Text("Продолжить"),
            onClick = {
                onEvent(CreateEvent.KeyScaled)
            }
        )
    }
}

@Composable
private fun KeyScale(
    modifier: Modifier,
    state: CreateScreenState.Scale,
    onKeyScale: (Float) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val minSizeValue = minOf(screenWidth, screenHeight)/2
    Box(modifier = modifier) {

        val scale = remember { mutableStateOf(state.initialScale) }
        val animateScale = animateFloatAsState(
            targetValue = scale.value,
            animationSpec = tween(durationMillis = 80,  easing = LinearEasing),
        )
        Box(
            modifier = Modifier.align(Alignment.Center),
            contentAlignment = Alignment.Center,
        ) {
            when(state.key.type) {
                KeyType.KWIKSET -> {
                    Key(
                        modifier = Modifier
                            .size(width = minSizeValue/3, height = minSizeValue)
                            .graphicsLayer {
                                scaleX = animateScale.value
                                scaleY = animateScale.value
                            },
                        borderColor = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        }
        Scaler(
            modifier = Modifier.align(Alignment.BottomCenter),
            initScale = scale.value,
            onChangeScale = { newScale ->
                scale.value = newScale
                onKeyScale(newScale)
            }
        )
    }
}

@Composable
fun Scaler(
    modifier: Modifier = Modifier,
    initScale: Float = 1f,
    maxScale: Float = 2f,
    minScale: Float = 0.5f,
    onChangeScale: (Float) -> Unit
) {
    var scale by remember { mutableStateOf(initScale) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(R.drawable.ic_minus_white),
            onClick = {
                scale = (scale - 0.025f).coerceAtMost(maxScale)
                onChangeScale(scale)
            }
        )

        Slider(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            value = scale,
            onValueChange = { newScale ->
                scale = newScale
                onChangeScale(newScale)
            },
            valueRange = minScale..maxScale,
        )

        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(R.drawable.ic_plus_white),
            onClick = {
                scale = (scale + 0.025f).coerceAtMost(maxScale)
                onChangeScale(scale)
            }
        )
    }
}
