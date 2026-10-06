package com.example.filmesfavoritos.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AzulCinema = Color(0xFF182848)
private val AzulClaro = Color(0xFF4B6CB7)
private val Dourado = Color(0xFFFFC857)
private val Fundo = Color(0xFFF5F7FA)

private val AppColors = lightColorScheme(
    primary = AzulCinema,
    secondary = AzulClaro,
    tertiary = Dourado,
    background = Fundo,
    surface = Color.White
)

@Composable
fun FilmesFavoritosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColors,
        typography = Typography(),
        content = content
    )
}
