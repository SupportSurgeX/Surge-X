package com.surgex.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SurgeColorScheme = darkColorScheme(
    primary = SurgeAccent,
    onPrimary = SurgeBlack,
    background = SurgeBlack,
    onBackground = SurgeWhite,
    surface = SurgeSurface,
    onSurface = SurgeWhite
)

@Composable
fun SurgeXTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SurgeColorScheme,
        typography = SurgeTypography,
        content = content
    )
}
