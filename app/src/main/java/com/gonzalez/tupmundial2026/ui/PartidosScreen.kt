package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PartidosScreen(viewModel: MundialViewModel) {
    val partidos = viewModel.partidosLista
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        viewModel.LlamarPartidos()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Partidos del Mundial 2026", style = MaterialTheme.typography.headlineMedium)

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(partidos) { partido ->
                    PartidoItem(partido = partido)
                }
            }
        }
    }
}

@Composable
fun PartidoItem(partido: DTOPartidosLista) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Aquí puedes cargar las banderas con Coil o Glide Compose
            Text(text = partido.flags1)  // placeholder
            Column {
                Text("${partido.equipo1} vs ${partido.equipo2}")
                Text("Fecha: ${partido.fecha}")
                Text("Estadio: ${partido.estadio}")
            }
            Text(text = partido.flags2)  // placeholder
        }
    }
}