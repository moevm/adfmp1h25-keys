package ru.etu.duplikeytor.presentation.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
@ReadOnlyComposable
fun Float.toDp(): Dp = LocalDensity.current.run { toDp() }

@Composable
@ReadOnlyComposable
fun Int.toDp(): Dp = LocalDensity.current.run { toDp() }

@Composable
@ReadOnlyComposable
fun Dp.toPx(): Int = with(LocalDensity.current) { toPx().toInt() }