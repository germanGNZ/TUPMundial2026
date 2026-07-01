package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import com.gonzalez.tupmundial2026.ui.Dorado
import com.gonzalez.tupmundial2026.ui.TarjetaBorde
import com.gonzalez.tupmundial2026.ui.TarjetaFondo
import com.gonzalez.tupmundial2026.ui.VerdeOscuro
import com.gonzalez.tupmundial2026.utils.formatFecha

// Tarjeta individual de partido para la lista.
// Separada acá para poder reutilizarla sin copiar código.

@Composable
fun PartidoItem(partido: DTOPartidosLista, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TarjetaFondo)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.width(4.dp).matchParentSize().background(TarjetaBorde))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 12.dp, top = 14.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(VerdeOscuro)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(partido.grupo ?: "", color = Dorado, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
                Spacer(Modifier.height(8.dp))
                Text("${partido.equipo1} vs ${partido.equipo2}", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text("📅 ${formatFecha(partido.fecha)}", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
                Text("🏟 ${partido.estadio}", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
            }
            Text("›", color = Dorado, fontSize = 28.sp)
        }
    }
}