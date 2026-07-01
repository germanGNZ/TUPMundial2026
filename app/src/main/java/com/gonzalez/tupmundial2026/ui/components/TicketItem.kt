package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.data.Ticket
import com.gonzalez.tupmundial2026.ui.Dorado
import com.gonzalez.tupmundial2026.ui.TarjetaFondo
import com.gonzalez.tupmundial2026.ui.VerdeOscuro
import com.gonzalez.tupmundial2026.utils.formatFecha
import com.gonzalez.tupmundial2026.utils.formatPrecio

// Tarjeta individual de ticket comprado.
// Separada acá para poder reutilizarla sin copiar código.

@Composable
fun TicketItem(ticket: Ticket) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TarjetaFondo)
    ) {
        Box(modifier = Modifier.width(4.dp).matchParentSize().background(Dorado))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 12.dp, top = 14.dp, bottom = 14.dp)
        ) {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(VerdeOscuro)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(ticket.grupo, color = Dorado, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
                Spacer(Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(ticket.sector, color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("${ticket.equipo1} vs ${ticket.equipo2}", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text("📅 ${formatFecha(ticket.fecha)}", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
            Text("🏟 ${ticket.estadio}", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
            Text("🎟 ${ticket.cantidadEntradas} entrada${if (ticket.cantidadEntradas > 1) "s" else ""}", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
            Text("💰 Total: ${formatPrecio(ticket.total)}", color = Dorado, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}