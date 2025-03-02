package ru.etu.duplikeytor.presentation.create.view.create

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.view.util.Key
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton

@Composable
internal fun CreateScreen(
    modifier: Modifier,
    state: CreateScreenState.Create,
    interfaceVisibleState: Boolean,
    onEvent: (CreateEvent) -> Unit,
) {
    val animateAlpha = animateFloatAsState(
        targetValue = if(interfaceVisibleState) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = EaseInOut,
        )
    )
    Box(
        modifier = modifier,
    ) {
        UiKitButton(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopEnd),
            button = ButtonState.Icon.Action(
                if (interfaceVisibleState) {
                    R.drawable.ic_eye_closed_black
                } else {
                    R.drawable.ic_eye_opened_black
                }
            ),
            onClick = {
                onEvent(CreateEvent.InterfaceVisibleChange)
            },
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            val currentPinNumber = remember { mutableStateOf(1) }
            val currentPinDeep = remember { mutableStateOf(0) }
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                Selector(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.Bottom)
                        .alpha(animateAlpha.value),
                    title = "Пин",
                    initValue = 1,
                    minValue = 1,
                    maxValue = 5,
                    onChange = { number ->
                        currentPinNumber.value = number
                    }
                )
                KeyCreate(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    state = state,
                )
                Selector(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.Bottom)
                        .alpha(animateAlpha.value),
                    title = "Глубина",
                    initValue = 0,
                    minValue = 0,
                    maxValue = 4,
                    onChange = { number ->
                        currentPinDeep.value = number
                    }
                )
            }

            Spacer(Modifier.height(10.dp))

            UiKitButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .alpha(animateAlpha.value),
                button = ButtonState.Text("Продолжить"),
                onClick = {
                    onEvent(CreateEvent.KeyCreated)
                }
            )
        }
    }
}

@Composable
private fun Selector(
    modifier: Modifier,
    title: String,
    initValue: Int,
    minValue: Int,
    maxValue: Int,
    onChange: (Int) -> Unit,
) {
    val currentValue = remember { mutableIntStateOf(initValue) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(R.drawable.ic_plus_white),
            onClick = {
                if (currentValue.intValue+1 in minValue..maxValue) {
                    currentValue.intValue += 1
                    onChange(currentValue.intValue)
                }
            }
        )
        Text(
            modifier = Modifier.padding(vertical = 15.dp),
            text = currentValue.intValue.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(R.drawable.ic_minus_white),
            onClick = {
                if (currentValue.intValue-1 in minValue..maxValue) {
                    currentValue.intValue -= 1
                    onChange(currentValue.intValue)
                }
            }
        )
    }
}

@Composable
private fun KeyCreate(
    modifier: Modifier,
    state: CreateScreenState.Create,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val minSizeValue = minOf(screenWidth, screenHeight)/2
    Box(
        modifier = modifier,
    ) {
        Key(
            modifier = Modifier
                .size(width = minSizeValue/3, height = minSizeValue)
                .graphicsLayer {
                    scaleX = state.scale
                    scaleY = state.scale
                }
                .align(Alignment.Center),
            borderColor = MaterialTheme.colorScheme.onBackground,
        )
    }
}
