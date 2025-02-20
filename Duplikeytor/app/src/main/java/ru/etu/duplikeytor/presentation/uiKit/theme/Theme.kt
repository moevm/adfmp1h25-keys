package ru.etu.duplikeytor.presentation.uiKit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    onPrimary = FillWhite,
    secondary = Yellow,
    surface = LightGrey,
    onSurface = FillWhite,
    background = DarkGrey,
    onBackground = FadeWhite,
    errorContainer = Red
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = FillWhite,
    secondary = Yellow,
    surface = FillWhite,
    onSurface = FillBlack,
    background = NighWhite,
    onBackground = DarkGrey,
    errorContainer = Red
)

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}