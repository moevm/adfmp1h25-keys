package ru.etu.duplikeytor.presentation.navigation.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.navigation.model.NavigationBarState
import ru.etu.duplikeytor.presentation.navigation.model.NavigationButtonState

@Composable
internal fun NavigationBar(
    modifier: Modifier,
    state: NavigationBarState,
    paddingValues: PaddingValues, // TODO костыль
) {
    val selectedButton = remember { mutableIntStateOf(0) } // TODO remove
    val bottomPadding = paddingValues.calculateBottomPadding() // TODO костыль
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp+bottomPadding)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            state.buttons.forEachIndexed{ index, buttonState ->
                val isSelected = mutableStateOf(index == selectedButton.intValue)
                NavigationButton(
                    modifier = Modifier,
                    state = buttonState,
                    onClick = { selectedButton.intValue = index },
                    isSelected = isSelected,
                )
            }
        }
    }
}

@Preview(widthDp = 450, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NavigationBarPreview() {
    NavigationBar(
        modifier = Modifier,
        state = NavigationBarState(
            buttons = listOf(
                NavigationButtonState(
                    iconRes = R.drawable.ic_key_white,
                    onClick = {},
                ),
                NavigationButtonState(
                    iconRes = R.drawable.ic_saved_white,
                    onClick = {},
                ),
                NavigationButtonState(
                    iconRes = R.drawable.ic_about_white,
                    onClick = {},
                )
            )
        ),
        paddingValues = PaddingValues(),
    )
}