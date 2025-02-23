package ru.etu.duplikeytor.presentation.holder.view.navbar

import android.content.res.Configuration
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton
import kotlin.math.roundToInt

@Composable
internal fun NavigationBar(
    modifier: Modifier = Modifier,
    state: NavigationBarState,
    currentScreen: () -> ScreenType,
    onClick: (ScreenType) -> Unit,
) {
    val bottomPadding = contentWindowInsets.asPaddingValues().calculateBottomPadding()
    val navigationBarHeight = remember { mutableIntStateOf(0) }
    val animatedOffset = animateOffsetAsState(
        targetValue = if (state.requiredDisplay) {
            Offset(0f, 0f)
        } else {
            Offset(0f, navigationBarHeight.intValue.toFloat())
        },
        animationSpec = tween(
            durationMillis = 250,
            easing = EaseInOut,
        ),
        label = "hide status bar animation"
    )

    Box(
        modifier = modifier
            .offset { IntOffset(0, animatedOffset.value.y.roundToInt()) }
            .onGloballyPositioned { layoutCoordinates ->
                navigationBarHeight.intValue = layoutCoordinates.size.height
            }
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        NavigationControl(
            modifier = Modifier.padding(bottom = bottomPadding),
            state = state,
            currentScreen = currentScreen,
            onClick = onClick,
        )
    }
}

@Composable
private fun NavigationControl(
    modifier: Modifier,
    state: NavigationBarState,
    currentScreen: () -> ScreenType,
    onClick: (ScreenType) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(80.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        state.buttons.forEach { (buttonState, screen) ->
            val isSelected = remember {
                derivedStateOf {
                    screen == currentScreen()
                }
            }
            UiKitButton(
                modifier = Modifier,
                button = ButtonState.Icon.Navigation(
                    icon = buttonState.icon,
                    isSelected = isSelected.value,
                ),
                onClick = {
                    onClick(screen)
                },
            )
        }
    }
}

@Preview(widthDp = 450, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NavigationBarPreview() {
    NavigationBar(
        onClick = {},
        state = NavigationBarState.build(),
        currentScreen = { ScreenType.main },
    )
}