package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.TarjetaFondo
import com.gonzalez.tupmundial2026.ui.VerdeOscuro

// Selector de cantidad con botones + y −.
// Extraído de CompraTicketScreen para poder reutilizarlo si se necesita
// en otras pantallas.

@Composable
fun SelectorCantidad(
    cantidad: Int,
    onAumentar: () -> Unit,
    onDisminuir: () -> Unit,
    min: Int = 1,
    max: Int = 10
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TarjetaFondo)
            .padding(16.dp)
    ) {
        Column {
            Text("Cantidad de entradas", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(
                    onClick = { if (cantidad > min) onDisminuir() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (cantidad > min) VerdeOscuro else VerdeOscuro.copy(alpha = 0.4f))
                        .size(40.dp)
                ) {
                    Text("−", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
                Text("$cantidad", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                IconButton(
                    onClick = { if (cantidad < max) onAumentar() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (cantidad < max) VerdeOscuro else VerdeOscuro.copy(alpha = 0.4f))
                        .size(40.dp)
                ) {
                    Text("+", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}