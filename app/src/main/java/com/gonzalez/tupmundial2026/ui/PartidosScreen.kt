package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.components.HeaderMundial
import com.gonzalez.tupmundial2026.ui.components.PartidoItem
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

@Composable
fun PartidosScreen(
    viewModel: MundialViewModel,
    nombreUsuario: String?,
    onPartidoClick: (Int) -> Unit,
    onLogout: () -> Unit,
    onMisTickets: () -> Unit
) {
    val partidos = viewModel.partidosLista
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    LaunchedEffect(Unit) { viewModel.LlamarPartidos() }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        HeaderMundial(
            titulo = "Mundial 2026",
            subtitulo = if (nombreUsuario != null) "Hola, $nombreUsuario 👋" else "USA • Canada • México",
            accionDerecha = {
                Column(horizontalAlignment = Alignment.End) {
                    TextButton(onClick = onLogout) {
                        Text("Salir", color = Dorado, fontSize = 13.sp)
                    }
                    TextButton(onClick = onMisTickets, contentPadding = PaddingValues(0.dp)) {
                        Text("🎟 Mis tickets", color = Color.White.copy(alpha = 0.85f), fontSize = 13.sp)
                    }
                }
            }
        )

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Dorado)
                    Text("Cargando partidos...", color = Color.White.copy(alpha = 0.6f))
                }
            }
            error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(error, color = Color(0xFFEF5350), textAlign = TextAlign.Center, modifier = Modifier.padding(24.dp))
            }
            partidos.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay partidos disponibles.", color = Color.White.copy(alpha = 0.6f))
            }
            else -> LazyColumn(
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










