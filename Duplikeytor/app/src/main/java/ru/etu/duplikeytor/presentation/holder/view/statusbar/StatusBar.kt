package ru.etu.duplikeytor.presentation.holder.view.statusbar

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults.contentWindowInsets
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import kotlin.math.roundToInt

@Composable
internal fun StatusBar(
    modifier: Modifier = Modifier,
    state: StatusBarState,
) {
    val topPadding = contentWindowInsets.asPaddingValues().calculateTopPadding()
    val statusBarHeight = remember { mutableIntStateOf(0) }
    val animatedOffset = animateOffsetAsState(
        targetValue = if (state.requiredDisplay) {
            Offset(0f, 0f)
        } else {
            Offset(0f, -statusBarHeight.intValue.toFloat())
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
                statusBarHeight.intValue = layoutCoordinates.size.height
            }
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier = Modifier
                .padding(top = topPadding)
                .fillMaxWidth()
                .requiredHeight(40.dp),
        ) {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (state.onBackClick != null) {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .size(25.dp)
                            .clickable {
                                state.onBackClick?.let { it() }
                            },
                        painter = painterResource(R.drawable.ic_back_black),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                    )
                }
                Content(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (state.onBackClick != null) {
                                Modifier.padding(end = 55.dp)
                            } else Modifier
                        ),
                    state = state,
                )
            }
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    state: StatusBarState,
) {
    when(state) {
        is StatusBarState.Title -> {
            TextContent(
                modifier = modifier,
                state = state,
            )
        }
        is StatusBarState.Empty -> {}
    }
}

@Composable
private fun TextContent(
    modifier: Modifier,
    state: StatusBarState.Title,
) {
    Text(
        modifier = modifier.fillMaxWidth(),
        text = state.title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}