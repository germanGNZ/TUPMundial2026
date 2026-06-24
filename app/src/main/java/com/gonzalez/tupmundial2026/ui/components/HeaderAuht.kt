package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.Dorado
import com.gonzalez.tupmundial2026.ui.DoradoOscuro
import com.gonzalez.tupmundial2026.ui.VerdeMedio
import com.gonzalez.tupmundial2026.ui.VerdeOscuro

// Header específico para las pantallas de autenticación (Login y Registro).
// Es distinto al HeaderMundial: tiene emoji grande centrado y no tiene
// botón de volver ni acciones a la derecha.

@Composable
fun HeaderAuth(
    emoji: String,
    titulo: String,
    subtitulo: String
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                .padding(horizontal = 20.dp, vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(emoji, fontSize = 48.sp)
                Spacer(Modifier.height(8.dp))
                Text(titulo, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
                Text(subtitulo, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado)))
        )
    }
}