package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

@Composable
fun DetalleScreen(
    id: Int,
    viewModel: MundialViewModel,
    onBack: () -> Unit,
    onComprarEntrada: (DTOPartidosDetalle) -> Unit
) {
    val detalle = viewModel.partidosDetalle
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    LaunchedEffect(id) { viewModel.LlamarDetalle(id) }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        HeaderMundial(titulo = "Detalle del Partido", onBack = onBack)

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = Dorado) }
            error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(error, color = Color(0xFFEF5350)) }
            detalle == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No se encontró el partido.", color = Color.White) }
            else -> Column(modifier = Modifier.padding(20.dp)) {
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
                        .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(detalle.equipo1, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text("VS", color = Dorado, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 6.dp))
                        Text(detalle.equipo2, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(16.dp))
                DetalleCard("📅", "Fecha", formatFecha(detalle.fecha))
                DetalleCard("🏆", "Grupo", detalle.grupo)
                DetalleCard("🏟", "Estadio", detalle.estadio)
                DetalleCard("🎟", "Precio entrada", detalle.precio)
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = { onComprarEntrada(detalle) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Dorado)
                ) {
                    Text("🎟  Comprar entrada", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}