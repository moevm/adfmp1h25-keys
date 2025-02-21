package ru.etu.duplikeytor.presentation.navigation.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.presentation.navigation.model.NavigationBarState
import ru.etu.duplikeytor.presentation.navigation.model.ScreenType
import ru.etu.duplikeytor.presentation.ui.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.ui.uiKit.button.UiKitButton

@Composable
internal fun NavigationBar(
    modifier: Modifier = Modifier,
    onClick: (ScreenType) -> Unit,
    state: NavigationBarState,
) {
    val selectedButton = remember { mutableIntStateOf(state.initSelectedIndex) }
    val bottomPadding = contentWindowInsets.asPaddingValues().calculateBottomPadding()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = bottomPadding)
                .requiredHeight(80.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            state.buttons.forEachIndexed { index, (buttonState, route) ->
                val isSelected = remember {
                    derivedStateOf {
                        index == selectedButton.intValue
                    }
                }
                UiKitButton(
                    modifier = Modifier,
                    button = ButtonState.Icon.Navigation(
                        icon = buttonState.icon,
                        isSelected = isSelected.value,
                    ),
                    onClick = {
                        onClick(route)
                        selectedButton.intValue = index
                    },
                )
            }
        }
    }
}

@Preview(widthDp = 450, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NavigationBarPreview() {
    NavigationBar(
        onClick = {},
        state = NavigationBarState.build(),
    )
}