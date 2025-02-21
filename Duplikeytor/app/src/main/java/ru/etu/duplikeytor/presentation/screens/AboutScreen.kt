package ru.etu.duplikeytor.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
) {
    Box(modifier.fillMaxSize().background(Color.Cyan))
}