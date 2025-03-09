package ru.etu.duplikeytor.presentation.create.view.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig
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
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                val currentState = rememberSaveable { mutableStateOf(
                    when(state.keyConfig) {
                        is KeyConfig.Kwikset -> {
                            state.keyConfig.pins as MutableList
                        }
                        else -> {
                            mutableListOf(0, 0, 0, 0, 0) // TODO
                        }
                    }
                ) }
                val currentPinNumber = rememberSaveable { mutableIntStateOf(1) }
                val currentPinDeep = rememberSaveable { mutableIntStateOf(0) }
                AnimatedVisibility(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.Bottom),
                    visible = interfaceVisibleState,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Selector(
                        modifier = Modifier,
                        title = "Пин",
                        value = currentPinNumber.intValue,
                        minValue = 1,
                        maxValue = 5,
                        onChange = { number ->
                            currentPinNumber.intValue = number
                        }
                    )
                }
                KeyCreate(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    state = state,
                )
                AnimatedVisibility(
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.Bottom),
                    visible = interfaceVisibleState,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Selector(
                        modifier = Modifier,
                        title = "Глубина",
                        value = currentPinDeep.intValue,
                        minValue = 0,
                        maxValue = 4,
                        onChange = { number ->
                            currentPinDeep.intValue = number
                        }
                    )
                }

                LaunchedEffect(currentPinNumber.intValue) {
                    currentPinDeep.intValue = currentState.value[currentPinNumber.intValue - 1]
                }

                LaunchedEffect(currentPinDeep.intValue) {
                    currentState.value[currentPinNumber.intValue - 1] = currentPinDeep.intValue
                    onEvent(
                        CreateEvent.KeyCreateChanged(
                            pin = currentPinNumber.intValue,
                            deep = currentPinDeep.intValue,
                        )
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            UiKitButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .alpha(animateAlpha.value),
                button = ButtonState.Text("Продолжить"),
                onClick = {
                    if (interfaceVisibleState) {
                        onEvent(CreateEvent.KeyCreated)
                    }
                }
            )
        }
    }
}

@Composable
private fun Selector(
    modifier: Modifier,
    title: String,
    value: Int,
    minValue: Int,
    maxValue: Int,
    onChange: (Int) -> Unit,
) {
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
                if (value + 1 in minValue..maxValue) {
                    onChange(value + 1)
                }
            }
        )
        Text(
            modifier = Modifier.padding(vertical = 15.dp),
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge,
        )
        UiKitButton(
            modifier = Modifier,
            button = ButtonState.Icon.Default(R.drawable.ic_minus_white),
            onClick = {
                if (value - 1 in minValue..maxValue) {
                    onChange(value - 1)
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
