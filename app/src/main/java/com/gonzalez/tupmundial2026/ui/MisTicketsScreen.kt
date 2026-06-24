package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.components.HeaderMundial
import com.gonzalez.tupmundial2026.ui.components.TicketItem
import com.gonzalez.tupmundial2026.viewModel.TicketViewModel

@Composable
fun MisTicketsScreen(
    usuarioId: Int,
    ticketViewModel: TicketViewModel,
    onBack: () -> Unit
) {
    val tickets = ticketViewModel.misTickets
    val isLoading = ticketViewModel.isLoading
    val error = ticketViewModel.errorMessage

    // Total de entradas compradas
    val totalEntradas = tickets.sumOf { it.cantidadEntradas }

    LaunchedEffect(usuarioId) { ticketViewModel.cargarMisTickets(usuarioId) }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        HeaderMundial(
            titulo = "Mis Tickets",
            subtitulo = "Entradas compradas",
            onBack = onBack
        )

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Dorado)
            }
            error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(error, color = Color(0xFFEF5350), textAlign = TextAlign.Center, modifier = Modifier.padding(24.dp))
            }
            tickets.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎟", fontSize = 48.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("Todavía no compraste ninguna entrada.", color = Color.White.copy(alpha = 0.6f), textAlign = TextAlign.Center)
                }
            }
            else -> {
                // Resumen total
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(VerdeOscuro)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total de entradas compradas", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                        Text(
                            "$totalEntradas",
                            color = Dorado,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(tickets) { ticket -> TicketItem(ticket = ticket) }
                }
            }
        }
    }
}