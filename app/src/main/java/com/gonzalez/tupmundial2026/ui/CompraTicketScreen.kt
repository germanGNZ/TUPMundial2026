package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.ui.components.DetalleCard
import com.gonzalez.tupmundial2026.ui.components.HeaderMundial
import com.gonzalez.tupmundial2026.utils.formatFecha
import com.gonzalez.tupmundial2026.viewModel.TicketViewModel

@Composable
fun CompraTicketScreen(
    detalle: DTOPartidosDetalle,
    usuarioId: Int,
    ticketViewModel: TicketViewModel,
    onCompraExitosa: () -> Unit,
    onBack: () -> Unit
) {
    var cantidad by remember { mutableIntStateOf(1) }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        HeaderMundial(titulo = "Comprar Entrada", onBack = onBack)

        Column(modifier = Modifier.padding(20.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
                    .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(detalle.equipo1, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("VS", color = Dorado, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 4.dp))
                    Text(detalle.equipo2, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(16.dp))
            DetalleCard("📅", "Fecha", formatFecha(detalle.fecha))
            DetalleCard("🏟", "Estadio", detalle.estadio)
            DetalleCard("🏆", "Grupo", detalle.grupo)
            DetalleCard("🎟", "Precio por entrada", detalle.precio)
            Spacer(Modifier.height(20.dp))

            // Selector cantidad
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                    .background(TarjetaFondo).padding(16.dp)
            ) {
                Column {
                    Text("Cantidad de entradas", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        IconButton(
                            onClick = { if (cantidad > 1) cantidad-- },
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(VerdeOscuro).size(40.dp)
                        ) { Text("−", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
                        Text("$cantidad", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                        IconButton(
                            onClick = { if (cantidad < 10) cantidad++ },
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(VerdeOscuro).size(40.dp)
                        ) { Text("+", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) }
                    }
                }
            }

            ticketViewModel.errorMessage?.let { error ->
                Spacer(Modifier.height(12.dp))
                Box(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(Color(0xFF4E0000)).padding(12.dp)) {
                    Text(error, color = Color(0xFFEF9A9A), fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { ticketViewModel.comprar(usuarioId, detalle, cantidad, onCompraExitosa) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                enabled = !ticketViewModel.isLoading
            ) {
                if (ticketViewModel.isLoading)
                    CircularProgressIndicator(color = FondoOscuro, modifier = Modifier.size(20.dp))
                else
                    Text("Confirmar compra ($cantidad entrada${if (cantidad > 1) "s" else ""})", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}