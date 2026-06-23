package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import com.gonzalez.tupmundial2026.utils.formatFecha
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

@Composable
fun PartidosScreen(
    viewModel: MundialViewModel,
    nombreUsuario: String?,
    onPartidoClick: (Int) -> Unit,
    onLogout: () -> Unit
) {
    val partidos = viewModel.partidosLista
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    LaunchedEffect(Unit) { viewModel.LlamarPartidos() }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text("⚽ FIFA WORLD CUP", color = Dorado, fontSize = 13.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("Mundial 2026", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
                    if (nombreUsuario != null) {
                        Text("Hola, $nombreUsuario 👋", color = Color.White.copy(alpha = 0.75f), fontSize = 13.sp)
                    } else {
                        Text("USA • Canada • México", color = Color.White.copy(alpha = 0.75f), fontSize = 13.sp)
                    }
                }
                TextButton(onClick = onLogout) {
                    Text("Salir", color = Dorado, fontWeight = FontWeight.Bold)
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(3.dp)
            .background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado))))

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
            partidos.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay partidos disponibles.", color = Color.White.copy(alpha = 0.6f))
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











