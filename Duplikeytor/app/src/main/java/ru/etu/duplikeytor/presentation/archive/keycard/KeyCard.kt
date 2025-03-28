package ru.etu.duplikeytor.presentation.archive.keycard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.etu.duplikeytor.presentation.archive.model.KeyState
import ru.etu.duplikeytor.presentation.create.view.util.Key
import ru.etu.duplikeytor.presentation.shared.model.KeyType

@Composable
internal fun KeyCard(
    modifier: Modifier = Modifier,
    state: KeyState,
    onClick: (Long) -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 4.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp),
            )
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .fillMaxWidth()
            .clickable { onClick(state.id) },
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val placeholder = placeholderPainter(MaterialTheme.colorScheme.background)
            KeyPicture(
                modifier = Modifier.aspectRatio(1f),
                state = state,
                placeholder = placeholder,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                text = state.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 4.dp, end = 4.dp),
                text = state.createdAt,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun KeyPicture(
    modifier: Modifier = Modifier,
    state: KeyState,
    placeholder: Painter
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        val isError = remember { mutableStateOf(false) }
        if ((state.imageUri.isNullOrEmpty() || isError.value) && state.config != null) {
            Key(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
                    .aspectRatio(1 / state.config.sizeRatio),
                keyConfig = state.config,
                color = MaterialTheme.colorScheme.onBackground,
                borderColor = Color.Transparent,
                pinsColor = MaterialTheme.colorScheme.surface,
                pins = state.pins.split("-").map { it.toInt() },
            )
        } else if (state.imageUri != null && !isError.value) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = state.imageUri,
                contentDescription = null,
                onError = { isError.value = true },
                contentScale = ContentScale.Crop,
            )
        } else {
            AsyncImage(
                modifier = modifier.fillMaxSize().padding(10.dp),
                model = placeholder,
                contentDescription = null,
            )
        }
    }
}

private fun placeholderPainter(color: Color): Painter {
    return object : Painter() {
        override val intrinsicSize = Size.Unspecified

        override fun DrawScope.onDraw() {
            drawIntoCanvas { canvas: Canvas ->
                val paint = Paint().apply {
                    this.color = color
                }
                val size = size.minDimension
                val radius = size / 3
                canvas.drawCircle(
                    center = center,
                    radius = radius,
                    paint = paint,
                )
            }
        }
    }
}

@Preview(name = "KeyCard", widthDp = 300, heightDp = 400)
@Composable
private fun KeyCardPreview() {
    KeyCard(
        Modifier,
        KeyState(
            id = 0,
            scale = 1f,
            name = "Ключ от Нью-Йорка",
            imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
            createdAt = "12.02.2025 - 13:00",
            type = KeyType.KWIKSET,
            pins = "1-2-3-4-5"
        ),
        onClick = {}
    )
}
