package ru.etu.duplikeytor.presentation.create.view.choose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.choose.KeyChosenState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton
import ru.etu.duplikeytor.presentation.ui.utils.toDp
import kotlin.math.absoluteValue

private const val CARD_WIDTH = 200

@Composable
internal fun ChooseScreen(
    modifier: Modifier = Modifier,
    state: CreateScreenState.Choose,
    onEvent: (CreateEvent.KeyChoose) -> Unit
) {
    val currentKey = remember { mutableStateOf(state.keys.first()) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Chooser(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = state,
            onChangeKey = { chosenKey ->
                currentKey.value = chosenKey
            }
        )
        UiKitButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            button = ButtonState.Text("Выбрать"),
            onClick = {
                onEvent(CreateEvent.KeyChoose(currentKey.value))
            }
        )
    }
}

@Composable
private fun Chooser(
    modifier: Modifier,
    state: CreateScreenState.Choose,
    onChangeKey: (KeyChosenState) -> Unit,
) {
    val currentKey = remember { mutableStateOf(state.keys.first()) }
    val isUserInteracting = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val centerOffset = with(LocalDensity.current) { (CARD_WIDTH.dp / 2).toPx() }
    val snapFlingBehavior = rememberSnapFlingBehavior(listState)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier,
            flingBehavior = snapFlingBehavior,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = (screenWidthDp/2 - 250.toDp())) // TODO Fix
        ) {
            itemsIndexed(items = state.keys) { index, key ->
                val currentOffset = listState.layoutInfo.visibleItemsInfo
                    .find { it.index == index }
                    ?.let { it.offset + it.size / 2 } ?: 0
                val distanceFromCenter = currentOffset - centerOffset
                val textOffsetDp = (distanceFromCenter / 2).toDp()
                val alpha = calculateAlphaForOffset(distanceFromCenter.toInt())

                Column (
                    modifier = Modifier
                        .alpha(alpha)
                        .width(CARD_WIDTH.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .offset(x = textOffsetDp)
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        text = key.title,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                    AsyncImage(
                        modifier = Modifier
                            .height(400.dp)
                            .clickable {
                                if (currentKey.value != key) {
                                    currentKey.value = key
                                    isUserInteracting.value = true
                                }
                            },
                        model = key.imageUri,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                    )
                }
            }
        }
        Spacer(Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            state.keys.forEach { key ->
                val isSelected = remember { mutableStateOf(false) }
                val animateSize by animateDpAsState(
                    targetValue = if (isSelected.value) 12.dp else 8.dp,
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
                    label = ""
                )
                val animateColor by animateColorAsState(
                    targetValue = if (isSelected.value) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .align(Alignment.CenterVertically)
                        .clip(CircleShape)
                        .background(animateColor)
                        .size(animateSize)
                )

                LaunchedEffect(currentKey.value) {
                    isSelected.value = currentKey.value == key
                }
            }
        }
    }

    LaunchedEffect(currentKey.value) {
        onChangeKey(currentKey.value)
        if (isUserInteracting.value) {
            coroutineScope.launch {
                listState.animateScrollToItem(state.keys.indexOf(currentKey.value))
            }.invokeOnCompletion {
                isUserInteracting.value = false
            }
        }
    }


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .distinctUntilChanged()
            .collect { layoutInfo ->
                if (isUserInteracting.value) return@collect
                val viewportStart = layoutInfo.viewportStartOffset
                val viewportEnd = layoutInfo.viewportEndOffset
                val viewportCenter = (viewportStart + viewportEnd) / 2

                val closestItem = layoutInfo.visibleItemsInfo.minByOrNull {
                    val itemMiddle = it.offset + it.size / 2
                    kotlin.math.abs(itemMiddle - viewportCenter)
                }

                closestItem?.index?.let { index ->
                    if (!isUserInteracting.value) {
                        currentKey.value = state.keys[index]
                    }
                }
            }
    }
}

private fun calculateAlphaForOffset(offset: Int): Float {
    val maxDistance = 800
    return (1f - (offset.absoluteValue / maxDistance.toFloat())).coerceIn(0.3f, 1f)
}
