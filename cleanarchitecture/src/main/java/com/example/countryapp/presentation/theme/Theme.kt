package com.example.countryapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBBDEFB),
    onPrimaryContainer = Color(0xFF004881),
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    background = Color(0xFFF5F5F5),
    onBackground = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF004881),
    onPrimaryContainer = Color(0xFFBBDEFB),
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    surface = Color(0xFF121212),
    onSurface = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White
)

@Composable
fun CountryAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
