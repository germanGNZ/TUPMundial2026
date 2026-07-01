package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.Dorado
import com.gonzalez.tupmundial2026.ui.TarjetaFondo
import com.gonzalez.tupmundial2026.ui.VerdeOscuro

// Representa un sector/ubicación dentro del estadio.
// "multiplicador" se aplica sobre el precio base de la entrada.
data class Sector(
    val nombre: String,
    val descripcion: String,
    val multiplicador: Double
)

val sectoresDisponibles = listOf(
    Sector("Popular", "Acceso general, de pie", 0.8),
    Sector("Platea", "Asiento numerado", 1.0),
    Sector("VIP", "Zona premium con servicios exclusivos", 1.8)
)

// Selector de sector — tarjetas seleccionables, una por sector.
// Se usa en CompraTicketScreen para definir el precio final de la entrada.
@Composable
fun SelectorSector(
    sectorSeleccionado: Sector,
    onSectorClick: (Sector) -> Unit
) {
    Column {
        Text("Sector / ubicación", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
        Spacer(Modifier.height(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            sectoresDisponibles.forEach { sector ->
                val seleccionado = sector.nombre == sectorSeleccionado.nombre
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(TarjetaFondo)
                        .border(
                            width = if (seleccionado) 2.dp else 0.dp,
                            color = if (seleccionado) Dorado else Color.Transparent,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { onSectorClick(sector) }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(sector.nombre, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text(sector.descripcion, color = Color.White.copy(alpha = 0.55f), fontSize = 11.sp)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (seleccionado) Dorado else VerdeOscuro)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            if (sector.multiplicador == 1.0) "Precio base"
                            else if (sector.multiplicador > 1.0) "+${((sector.multiplicador - 1) * 100).toInt()}%"
                            else "-${((1 - sector.multiplicador) * 100).toInt()}%",
                            color = if (seleccionado) com.gonzalez.tupmundial2026.ui.FondoOscuro else Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}