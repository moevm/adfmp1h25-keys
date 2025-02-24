package ru.etu.duplikeytor.presentation.create.view.util


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun Key(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
    borderColor: Color,
    borderWidth: Dp = 4.dp
) {
    Canvas(modifier = modifier) {
        val halfBorderWidth = borderWidth.toPx() / 2

        val path = Path().apply {
            moveTo(size.width / 2, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - size.width / 2)
            lineTo(size.width / 2, size.height)
            lineTo(0f, size.height - size.width / 2)
            lineTo(0f, 0f)
            close()
        }

        drawPath(
            path = path,
            color = borderColor,
            style = Stroke(width = borderWidth.toPx())
        )

        val innerPath = Path().apply {
            moveTo(size.width / 2, halfBorderWidth)
            lineTo(size.width - halfBorderWidth, halfBorderWidth)
            lineTo(size.width - halfBorderWidth, size.height - size.width / 2)
            lineTo(size.width / 2, size.height - halfBorderWidth)
            lineTo(halfBorderWidth, size.height - size.width / 2)
            lineTo(halfBorderWidth, halfBorderWidth)
            close()
        }

        drawPath(
            path = innerPath,
            color = color
        )
    }
}

@Preview
@Composable
fun PreviewCustomArrow() {
    Key(
        modifier = Modifier.size(100.dp, 300.dp),
        borderColor = MaterialTheme.colorScheme.onBackground,
    )
}