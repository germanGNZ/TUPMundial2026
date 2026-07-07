package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.components.FiltroGrupos
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
    val textoBusqueda = viewModel.textoBusqueda

    var grupoSeleccionado by remember { mutableStateOf<String?>(null) }
    var faseSeleccionada by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(textoBusqueda) {
        if (textoBusqueda.isNotEmpty()) {
            grupoSeleccionado = null
            faseSeleccionada = null
        }
    }

    val grupos = remember(partidos) {
        partidos.mapNotNull { it.grupo }.distinct().sorted()
    }

    val partidosFiltrados = remember(partidos, grupoSeleccionado, faseSeleccionada, textoBusqueda) {
        when {
            textoBusqueda.isNotEmpty() -> partidos
            faseSeleccionada != null   -> partidos.filter { it.fase == faseSeleccionada }
            grupoSeleccionado != null  -> partidos.filter { it.grupo == grupoSeleccionado }
            else                       -> partidos
        }
    }

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

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { viewModel.buscar(it) },
            placeholder = { Text("Buscar equipo, grupo o estadio...", fontSize = 13.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Dorado) },
            trailingIcon = {
                if (textoBusqueda.isNotEmpty()) {
                    IconButton(onClick = {
                        viewModel.limpiarBusqueda()
                        grupoSeleccionado = null
                        faseSeleccionada = null
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar",
                            tint = Color.White.copy(alpha = 0.6f))
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Dorado,
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                focusedContainerColor = Color.White.copy(alpha = 0.05f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                cursorColor = Dorado
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Dorado)
                    Text("Cargando partidos...", color = Color.White.copy(alpha = 0.6f))
                }
            }
            error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(error, color = Color(0xFFEF5350), textAlign = TextAlign.Center,
                    modifier = Modifier.padding(24.dp))
            }
            partidos.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay partidos disponibles.", color = Color.White.copy(alpha = 0.6f))
            }
            else -> {
                if (textoBusqueda.isEmpty()) {
                    FiltroGrupos(
                        grupos = grupos,
                        grupoSeleccionado = grupoSeleccionado,
                        onGrupoClick = { grupoSeleccionado = it },
                        faseSeleccionada = faseSeleccionada,
                        onFaseClick = { faseSeleccionada = it }
                    )
                }

                if (partidosFiltrados.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "Sin resultados para \"$textoBusqueda\"",
                            color = Color.White.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(partidosFiltrados) { partido ->
                            PartidoItem(partido = partido, onClick = { onPartidoClick(partido.id) })
                        }
                    }
                }
            }
        }
    }
}










