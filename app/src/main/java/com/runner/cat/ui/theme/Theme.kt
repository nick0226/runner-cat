package com.runner.cat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = LimeGreen,
    secondary = LimeGreen,
    background = DarkGray,
    surface = MediumGray,
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onBackground = OnDarkGray,
    onSurface = OnDarkGray
)

private val LightColorScheme = darkColorScheme(
    primary = LimeGreen,
    secondary = LimeGreen,
    background = DarkGray,
    surface = MediumGray,
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onBackground = OnDarkGray,
    onSurface = OnDarkGray
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}