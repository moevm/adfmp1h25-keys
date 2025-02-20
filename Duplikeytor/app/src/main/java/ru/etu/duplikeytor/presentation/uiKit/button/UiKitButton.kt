package ru.etu.duplikeytor.presentation.uiKit.button

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.etu.duplikeytor.R

@Composable
fun  UiKitButton(
    modifier: Modifier,
    button: ButtonState,
    onClick: () -> Unit
) {
    when(button) {
        is ButtonState.Icon -> ButtonHolder(
            modifier = modifier
                .background(color = if (button is ButtonState.Icon.Default) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.errorContainer
                })
                .clickable {
                    onClick()
                },
        ) {
            IconContent(
                icon = button.icon
            )
        }
        is ButtonState.Text -> ButtonHolder(
            modifier = modifier
                .background(color = MaterialTheme.colorScheme.secondary)
                .clickable {
                    onClick()
                },
        ) {
            TextContent(
                text = button.text
            )
        }
    }
}

@Composable
private fun ButtonHolder(
    modifier: Modifier,
    content: @Composable (() -> Unit)
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .then(modifier)
            .widthIn(min = 64.dp)
            .requiredHeight(64.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun IconContent(
    modifier: Modifier = Modifier,
    icon: Int,
) {
    Icon(
        modifier = modifier.padding(12.dp),
        painter = painterResource(icon),
        tint = Color.Unspecified,
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
        color = Color.Black,
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