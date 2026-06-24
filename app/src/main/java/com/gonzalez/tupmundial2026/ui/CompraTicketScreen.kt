package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.ui.components.*
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
            // Card equipos — extraída a su propio componente
            EquiposVsCard(equipo1 = detalle.equipo1, equipo2 = detalle.equipo2)
            Spacer(Modifier.height(16.dp))
            DetalleCard("📅", "Fecha", formatFecha(detalle.fecha))
            DetalleCard("🏟", "Estadio", detalle.estadio)
            DetalleCard("🏆", "Grupo", detalle.grupo)
            DetalleCard("🎟", "Precio por entrada", detalle.precio)
            Spacer(Modifier.height(20.dp))

            // Selector de cantidad — extraído a su propio componente
            SelectorCantidad(
                cantidad = cantidad,
                onAumentar = { cantidad++ },
                onDisminuir = { cantidad-- }
            )

            // Mensaje de error — extraído a su propio componente
            ticketViewModel.errorMessage?.let {
                Spacer(Modifier.height(12.dp))
                MensajeError(it)
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
                    Text(
                        "Confirmar compra ($cantidad entrada${if (cantidad > 1) "s" else ""})",
                        color = FondoOscuro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
            }
        }
    }
}