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

private val FASES_ELIMINATORIAS = listOf(
    "Dieciseisavos de Final",
    "Octavos de Final",
    "Cuartos de Final",
    "Semifinal",
    "Tercer Puesto",
    "Final"
)

private fun etiquetaFase(fase: String) = when (fase) {
    "Dieciseisavos de Final" -> "16avos"
    "Octavos de Final"       -> "Octavos"
    "Cuartos de Final"       -> "Cuartos"
    "Semifinal"              -> "Semis"
    "Tercer Puesto"          -> "3er Puesto"
    "Final"                  -> "⭐ Final"
    else                     -> fase
}

@Composable
fun FiltroGrupos(
    grupos: List<String>,
    grupoSeleccionado: String?,
    onGrupoClick: (String?) -> Unit,
    faseSeleccionada: String? = null,
    onFaseClick: ((String?) -> Unit)? = null
) {
    Column {
        // Fila 1: Todos + Grupos A-L
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ChipFiltro(
                    texto = "Todos",
                    seleccionado = grupoSeleccionado == null && faseSeleccionada == null,
                    onClick = {
                        onGrupoClick(null)
                        onFaseClick?.invoke(null)
                    }
                )
            }
            items(grupos) { grupo ->
                ChipFiltro(
                    texto = grupo,
                    seleccionado = grupoSeleccionado == grupo,
                    onClick = {
                        onGrupoClick(grupo)
                        onFaseClick?.invoke(null)
                    }
                )
            }
        }

        // Fila 2: Fases eliminatorias
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(FASES_ELIMINATORIAS) { fase ->
                ChipFiltro(
                    texto = etiquetaFase(fase),
                    seleccionado = faseSeleccionada == fase,
                    colorSeleccionado = when (fase) {
                        "Final"            -> Color(0xFFFFD700)
                        "Semifinal"        -> Color(0xFFE0E0E0)
                        "Cuartos de Final" -> Color(0xFFCD7F32)
                        else               -> Dorado
                    },
                    onClick = {
                        onFaseClick?.invoke(if (faseSeleccionada == fase) null else fase)
                        onGrupoClick(null)
                    }
                )
            }
        }
    }
}

@Composable
private fun ChipFiltro(
    texto: String,
    seleccionado: Boolean,
    colorSeleccionado: Color = Dorado,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (seleccionado) colorSeleccionado else VerdeOscuro)
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