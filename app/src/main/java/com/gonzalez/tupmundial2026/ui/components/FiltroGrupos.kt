package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.Dorado
import com.gonzalez.tupmundial2026.ui.FondoOscuro
import com.gonzalez.tupmundial2026.ui.VerdeOscuro

// Chips horizontales para filtrar partidos por grupo.
// Se usa en PartidosScreen. "Todos" muestra todos los partidos.

@Composable
fun FiltroGrupos(
    grupos: List<String>,
    grupoSeleccionado: String?,
    onGrupoClick: (String?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Chip "Todos"
        item {
            ChipGrupo(
                texto = "Todos",
                seleccionado = grupoSeleccionado == null,
                onClick = { onGrupoClick(null) }
            )
        }
        items(grupos) { grupo ->
            ChipGrupo(
                texto = grupo,
                seleccionado = grupoSeleccionado == grupo,
                onClick = { onGrupoClick(grupo) }
            )
        }
    }
}

@Composable
private fun ChipGrupo(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (seleccionado) Dorado else VerdeOscuro)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = texto,
            color = if (seleccionado) FondoOscuro else Color.White.copy(alpha = 0.85f),
            fontSize = 12.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
        )
    }
}