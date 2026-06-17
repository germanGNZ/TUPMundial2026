package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel
@Composable
fun DetalleScreen(
    id: Int,
    viewModel: MundialViewModel,
    onBack: () -> Unit
) {
    val detalle = viewModel.partidosDetalle
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    LaunchedEffect(id) { viewModel.LlamarDetalle(id) }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Column {
                TextButton(onClick = onBack, contentPadding = PaddingValues(0.dp)) {
                    Text("← Volver", color = Dorado, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(4.dp))
                Text("Detalle del Partido", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado))))

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = Dorado) }
            error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(error, color = Color(0xFFEF5350)) }
            detalle == null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No se encontró el partido.", color = Color.White) }
            else -> DetalleContenido(detalle)
        }
    }
}

@Composable
private fun DetalleContenido(detalle: DTOPartidosDetalle) {
    Column(modifier = Modifier.padding(20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
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
    }
}

@Composable
private fun DetalleCard(emoji: String, label: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(TarjetaFondo)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 20.sp)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(label, color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp, letterSpacing = 1.sp)
            Text(valor, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}