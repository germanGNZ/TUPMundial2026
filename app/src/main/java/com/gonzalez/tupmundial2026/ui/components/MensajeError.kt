package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Bloque de error rojo reutilizable.
// Aparecía repetido en LoginScreen, RegisterScreen y CompraTicketScreen.

@Composable
fun MensajeError(mensaje: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF4E0000))
            .padding(12.dp)
    ) {
        Text(mensaje, color = Color(0xFFEF9A9A), fontSize = 13.sp)
    }
}