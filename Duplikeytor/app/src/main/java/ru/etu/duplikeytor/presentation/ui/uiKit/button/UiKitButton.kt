package ru.etu.duplikeytor.presentation.ui.uiKit.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R

@Composable
fun  UiKitButton(
    modifier: Modifier,
    button: ButtonState,
    onClick: () -> Unit
) {
    when(button) {
        is ButtonState.Icon -> {
            UiKitIconButton(
                modifier = modifier,
                button = button,
                onClick = onClick
            )
        }
        is ButtonState.Text -> {
            UiKitTextButton(
                modifier = modifier,
                button = button,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun UiKitIconButton(
    modifier: Modifier,
    button: ButtonState.Icon,
    onClick: () -> Unit,
) {
    val (color, iconColor) = when (button) {
        is ButtonState.Icon.Default -> {
            MaterialTheme.colorScheme.primary to Color.Unspecified
        }
        is ButtonState.Icon.Warning -> {
            MaterialTheme.colorScheme.errorContainer to Color.Unspecified
        }
        is ButtonState.Icon.Action -> {
            MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.onSecondary
        }
        is ButtonState.Icon.Navigation -> {
            if (button.isSelected) {
                MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.onPrimary
            } else {
                Color.Transparent to MaterialTheme.colorScheme.onSurface
            }
        }
    }

    ButtonHolder(
        modifier = modifier,
        color = color,
        onClick = onClick,
    ) {
        IconContent(
            icon = button.icon,
            iconColor = iconColor
        )
    }
}

@Composable
private fun UiKitTextButton(
    modifier: Modifier,
    button: ButtonState.Text,
    onClick: () -> Unit,
) {
    ButtonHolder(
        modifier = modifier,
        color =  MaterialTheme.colorScheme.secondary,
        onClick = onClick,
    ) { contentModifier ->
        TextContent(
            modifier = contentModifier,
            text = button.text
        )
    }
}

@Composable
private fun ButtonHolder(
    modifier: Modifier,
    color: Color,
    onClick: () -> Unit,
    content: @Composable ((Modifier) -> Unit)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(color)
            .widthIn(min = 64.dp)
            .requiredHeight(64.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        content(Modifier.align(Alignment.Center))
    }
}

@Composable
private fun IconContent(
    modifier: Modifier = Modifier,
    icon: Int,
    iconColor: Color = Color.Unspecified,
) {
    Icon(
        modifier = modifier.padding(12.dp),
        painter = painterResource(icon),
        tint = iconColor,
        contentDescription = null,
    )
}

@Composable
private fun TextContent(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier.padding(horizontal = 22.dp),
        text = text,
        color = MaterialTheme.colorScheme.onSecondary,
        style = MaterialTheme.typography.labelLarge,
    )
}

@Preview
@Composable
private fun UiKitButtonDefaultIconPreview() {
    UiKitButton(
        modifier = Modifier,
        button = ButtonState.Icon.Default(
            icon = R.drawable.ic_key_white,
        ),
        onClick = { }
    )
}

@Preview
@Composable
private fun UiKitButtonWarningIconPreview() {
    UiKitButton(
        modifier = Modifier,
        button = ButtonState.Icon.Warning(
            icon = R.drawable.ic_trash_black,
        ),
        onClick = { }
    )
}

@Preview
@Composable
private fun UiKitButtonTextPreview() {
    UiKitButton(
        modifier = Modifier,
        button = ButtonState.Text(
            text = "Yellow",
        ),
        onClick = { }
    )
}