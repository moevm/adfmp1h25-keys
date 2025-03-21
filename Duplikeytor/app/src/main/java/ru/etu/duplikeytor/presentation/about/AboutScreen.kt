package ru.etu.duplikeytor.presentation.about

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ru.etu.duplikeytor.presentation.about.model.AboutScreenState
import ru.etu.duplikeytor.presentation.about.model.DepartmentState
import ru.etu.duplikeytor.presentation.about.model.DeveloperState

@Composable
internal fun AboutScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    state: AboutScreenState,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPadding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Над приложением работали:",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
        val uriHandler = LocalUriHandler.current
        DeveloperTileList(
            developers = state.developers,
            uriHandler = uriHandler,
        )
        DepartmentLink(
            modifier = Modifier.weight(1f),
            uriHandler = uriHandler,
            state = state.department,
        )
    }
}

@Composable
private fun DeveloperTile(
    modifier: Modifier = Modifier,
    developerPhoto: String,
    developerName: String,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(80.dp)
            .clip(RoundedCornerShape(1000.dp))
            .background(MaterialTheme.colorScheme.surface)
            .then(modifier)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val placeHolder = placeholderPainter(MaterialTheme.colorScheme.background)
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(1000.dp)),
            model = developerPhoto,
            contentDescription = null,
            placeholder = placeHolder,
            error = placeHolder,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = developerName,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun DeveloperTileList(
    developers: List<DeveloperState>,
    uriHandler: UriHandler,
) {
    developers.forEach { developer ->
        DeveloperTile(
            modifier = Modifier
                .clickable { uriHandler.openUri(developer.githubUrl) },
            developerPhoto = developer.photoUrl,
            developerName = developer.nickname,
        )
    }
}

@Composable
private fun DepartmentLink(
    modifier: Modifier,
    uriHandler: UriHandler,
    state: DepartmentState,
) {
    Box(
        modifier = modifier.wrapContentWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
                .clickable { uriHandler.openUri(state.uri) }
                .fillMaxWidth()
                .alpha(0.5f),
            text = state.name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
        )
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
                val radius = size / 2
                canvas.drawCircle(
                    center = center,
                    radius = radius,
                    paint = paint
                )
            }
        }
    }
}
