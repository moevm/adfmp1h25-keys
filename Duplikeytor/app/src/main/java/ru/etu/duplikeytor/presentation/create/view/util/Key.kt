package ru.etu.duplikeytor.presentation.create.view.util


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun Key(
    modifier: Modifier = Modifier,
    pins: List<Int> = emptyList(),
    color: Color = Color.Red,
    borderColor: Color,
    pinsColor: Color = MaterialTheme.colorScheme.background,
    borderWidth: Dp = 4.dp
) {
    Canvas(modifier = modifier) {
        val halfBorderWidth = borderWidth.toPx() / 2

        val unitWidth = 335f
        val unitScale = size.width / unitWidth

        fun Float.u() = this * unitScale

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

        drawPath(path = innerPath, color = color)

        val pinSpacing = 150f.u()
        val baseY = (size.height - size.width / 2) - 150f.u()

        pins.forEachIndexed { index, depth ->
            val effectiveDepth = when {
                depth < 1 -> 0
                depth > 7 -> 6
                else -> depth - 1
            }

            if (effectiveDepth > 0) {
                val yCenter = baseY - index * pinSpacing
                val maxDepth = 138f.u()
                val pinDepth = maxDepth * effectiveDepth / 6

                val topWidth = 84f.u()
                val baseWidth = topWidth + 2 * pinDepth

                val path = Path().apply {
                    moveTo(halfBorderWidth - 1, yCenter - baseWidth/2)
                    lineTo(halfBorderWidth - 1 + pinDepth, yCenter - topWidth/2)
                    lineTo(halfBorderWidth - 1 + pinDepth, yCenter + topWidth/2)
                    lineTo(halfBorderWidth - 1, yCenter + baseWidth/2)
                    close()
                }

                drawPath(
                    path = path,
                    color = pinsColor,
                    style = Fill,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomArrow() {
    Key(
        modifier = Modifier.size(100.dp, 400.dp),
        borderColor = Color.Transparent,
        pinsColor = MaterialTheme.colorScheme.onBackground,
        pins = listOf(3, 2, 3, 4, 7),
    )
}