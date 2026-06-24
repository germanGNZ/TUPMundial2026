package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gonzalez.tupmundial2026.ui.Dorado

// Estilo unificado para todos los campos de texto de la app.
// Se usa en LoginScreen y RegisterScreen para no repetir los mismos colores.

@Composable
fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Dorado,
    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLabelColor = Dorado,
    unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
    cursorColor = Dorado
)