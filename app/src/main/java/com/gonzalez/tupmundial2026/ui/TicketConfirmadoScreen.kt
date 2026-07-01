package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.components.DetalleCard
import com.gonzalez.tupmundial2026.ui.components.EquiposVsCard
import com.gonzalez.tupmundial2026.utils.formatFecha
import com.gonzalez.tupmundial2026.utils.formatPrecio
import com.gonzalez.tupmundial2026.data.Ticket

// Pantalla que se muestra después de confirmar una compra exitosa.
// Muestra el resumen del ticket comprado antes de volver a la lista.

@Composable
fun TicketConfirmadoScreen(
    ticket: Ticket,
    onVerMisTickets: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(FondoOscuro).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        // Icono de éxito
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(40.dp))
                .background(VerdeOscuro),
            contentAlignment = Alignment.Center
        ) {
            Text("✓", color = Dorado, fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))
        Text("¡Compra exitosa!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Text(
            "Tu entrada fue reservada correctamente",
            color = Color.White.copy(alpha = 0.6f),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(32.dp))

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            EquiposVsCard(equipo1 = ticket.equipo1, equipo2 = ticket.equipo2)
            Spacer(Modifier.height(16.dp))
            DetalleCard("📅", "Fecha", formatFecha(ticket.fecha))
            DetalleCard("🏟", "Estadio", ticket.estadio)
            DetalleCard("🏆", "Grupo", ticket.grupo)
            DetalleCard("📍", "Sector", ticket.sector)
            DetalleCard("🎟", "Entradas", "${ticket.cantidadEntradas} entrada${if (ticket.cantidadEntradas > 1) "s" else ""}")
            DetalleCard("💳", "Método de pago", ticket.metodoPago)
            DetalleCard("👤", "Comprador", "${ticket.nombreComprador} (DNI ${ticket.dniComprador})")
            DetalleCard("💰", "Total pagado", formatPrecio(ticket.total))

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onVerMisTickets,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Dorado)
            ) {
                Text("Ver mis tickets", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdeOscuro)
            ) {
                Text("Volver a partidos", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}