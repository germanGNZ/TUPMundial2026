package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

private val VerdeOscuro = Color(0xFF1B5E20)
private val VerdeMedio = Color(0xFF2E7D32)
private val Dorado = Color(0xFFFFC107)
private val DoradoOscuro = Color(0xFFF57F17)
private val FondoOscuro = Color(0xFF121212)
private val TarjetaFondo = Color(0xFF1E1E1E)
private val TarjetaBorde = Color(0xFF2E7D32)

@Composable
fun PartidosScreen(
    viewModel: MundialViewModel,
    onPartidoClick: (Int) -> Unit
) {
    val partidos = viewModel.partidosLista
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    LaunchedEffect(Unit) { viewModel.LlamarPartidos() }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column {
                Text("⚽ FIFA WORLD CUP", color = Dorado, fontSize = 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                Spacer(Modifier.height(4.dp))
                Text("Mundial 2026", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                Text("USA • Canada • México", color = Color.White.copy(alpha = 0.75f), fontSize = 13.sp)
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado))))

        when {
            isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Dorado)
                        Spacer(Modifier.height(12.dp))
                        Text("Cargando partidos...", color = Color.White.copy(alpha = 0.6f))
                    }
                }
            }
            error != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(error, color = Color(0xFFEF5350), textAlign = TextAlign.Center, modifier = Modifier.padding(24.dp))
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(partidos) { partido ->
                        PartidoItem(partido = partido, onClick = { onPartidoClick(partido.id) })
                    }
                }
            }
        }
    }
}

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
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 12.dp, top = 14.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(VerdeOscuro).padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(partido.grupo, color = Dorado, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
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

@Composable
fun PartidoDetalleScreen(
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

private fun formatFecha(fecha: String): String {
    return try {
        val partes = fecha.split("T")
        val dia = partes[0].split("-").reversed().joinToString("/")
        val hora = partes[1].substring(0, 5)
        "$dia $hora hs"
    } catch (_: Exception) { fecha }
}