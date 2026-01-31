package ru.alexeypostnov.effectivemobilecourses.core.ui.components

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingComponent(modifier: Modifier = Modifier, color: Color = ProgressIndicatorDefaults.linearColor) {
    LinearProgressIndicator(
        modifier = modifier,
        trackColor = color.copy(alpha = 0.2f),
        color = color
    )
}