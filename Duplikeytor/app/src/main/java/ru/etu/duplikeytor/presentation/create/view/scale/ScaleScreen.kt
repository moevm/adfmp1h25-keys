package ru.etu.duplikeytor.presentation.create.view.scale

import androidx.compose.animation.core.EaseInOut
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
import androidx.compose.foundation.layout.width
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
import ru.etu.duplikeytor.presentation.create.model.chose.KeyChooseState
import ru.etu.duplikeytor.presentation.create.view.util.Key
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton

@Composable
internal fun ScaleScreen(
    modifier: Modifier = Modifier,
    state: CreateScreenState.Scale,
    onEvent: (CreateEvent.KeyScaled) -> Unit
) {
    val currentScale = remember { mutableStateOf(0f) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            text = stringResource(R.string.description_scale_key),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )

        KeyScale(
            modifier = Modifier.fillMaxWidth().weight(1f),
            state = state.key
        )

        Spacer(Modifier.height(10.dp))

        UiKitButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            button = ButtonState.Text("Продолжить"),
            onClick = {
                onEvent(CreateEvent.KeyScaled(currentScale.value))
            }
        )
    }
}

@Composable
private fun KeyScale(
    modifier: Modifier,
    state: KeyChooseState
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val scale = remember { mutableStateOf(1f) }
        val animateScale = animateFloatAsState(
            targetValue = scale.value,
            animationSpec = tween(durationMillis = 80,  easing = EaseInOut),
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Key(
                modifier = Modifier
                    .height(screenWidth/2)
                    .width(screenWidth/6)
                    .graphicsLayer {
                        scaleX = animateScale.value
                        scaleY = animateScale.value
                    },
                borderColor = MaterialTheme.colorScheme.onBackground,
            )
        }
        Scaler(
            modifier = Modifier,
            onChangeScale = { newScale -> scale.value = newScale }
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
                onChangeScale(scale)
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
