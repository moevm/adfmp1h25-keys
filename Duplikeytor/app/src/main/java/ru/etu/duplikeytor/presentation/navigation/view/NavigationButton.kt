package ru.etu.duplikeytor.presentation.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.navigation.model.NavigationButtonState

@Composable
internal fun NavigationButton(
    modifier: Modifier,
    state: NavigationButtonState,
    isSelected: State<Boolean>,
    onClick: () -> Unit,
) {
    Box( // TODO use UiKitButton
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .size(64.dp)
            .colorOnSelected(isSelected.value)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.padding(12.dp),
            painter = painterResource(state.iconRes),
            tint = getIconColor(isSelected.value),
            contentDescription = null,
        )
    }
}

@ReadOnlyComposable
@Composable
private fun getIconColor(isSelected: Boolean) = when {
    isSystemInDarkTheme() -> MaterialTheme.colorScheme.onSurface
    isSelected -> MaterialTheme.colorScheme.onPrimary
    else -> MaterialTheme.colorScheme.onSurface
}

@Composable
private fun Modifier.colorOnSelected(isSelected: Boolean) = this.then(
    if (isSelected) {
        Modifier.background(MaterialTheme.colorScheme.primary)
    } else {
        Modifier
    }
)

@Preview
@Composable
private fun NavigationButtonPreview() {
    NavigationButton(
        modifier = Modifier,
        state = NavigationButtonState(
            iconRes = R.drawable.ic_key_white,
            onClick = {},
        ),
        isSelected = remember { mutableStateOf(true) },
        onClick = {},
    )
}