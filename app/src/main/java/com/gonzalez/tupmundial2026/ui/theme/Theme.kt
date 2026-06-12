package com.gonzalez.tupmundial2026.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DoradoTrofeo,
    onPrimary = GrisOscuro,
    secondary = VerdeCanchaClaro,
    onSecondary = BlancoNieve,
    background = GrisOscuro,
    onBackground = BlancoNieve,
    surface = GrisMedio,
    onSurface = BlancoNieve,
)

private val LightColorScheme = lightColorScheme(
    primary = VerdeCancha,
    onPrimary = BlancoNieve,
    secondary = DoradoTrofeo,
    onSecondary = GrisOscuro,
    background = Color(0xFFF1F8E9),
    onBackground = GrisOscuro,
    surface = BlancoNieve,
    onSurface = GrisOscuro,
)

@Composable
fun TUPMundial2026Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

