package com.works.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Primary, onPrimary = OnPrimary, secondary = Secondary,
    background = Background, surface = Surface, error = Error,
    onBackground = OnBackground, onSurface = OnBackground,
)

@Composable
fun ECommerceTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColors, content = content)
}
