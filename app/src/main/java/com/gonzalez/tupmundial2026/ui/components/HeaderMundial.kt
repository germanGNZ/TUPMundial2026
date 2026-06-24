package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

// Header reutilizable con gradiente verde y línea dorada.
// Se usa en PartidosScreen, DetallesScreen, CompraTicketScreen y MisTicketsScreen.

@Composable
fun HeaderMundial(
    titulo: String,
    subtitulo: String? = null,
    onBack: (() -> Unit)? = null,        // si no es null, muestra "← Volver"
    accionDerecha: (@Composable () -> Unit)? = null  // botón opcional arriba a la derecha
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                if (onBack != null) {
                    TextButton(onClick = onBack, contentPadding = PaddingValues(0.dp)) {
                        Text("← Volver", color = Dorado, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(4.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(titulo, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                        if (subtitulo != null) {
                            Text(subtitulo, color = Color.White.copy(alpha = 0.75f), fontSize = 13.sp)
                        }
                    }
                    if (accionDerecha != null) {
                        accionDerecha()
                    }
                }
            }
        }
        // Línea dorada separadora
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado)))
        )
    }
}