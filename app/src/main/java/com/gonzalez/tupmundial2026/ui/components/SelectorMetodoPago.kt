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

// Métodos de pago disponibles (simulados, no se procesa pago real).
val metodosPago = listOf(
    "💳 Tarjeta de crédito/débito",
    "🅿 MercadoPago",
    "💵 Efectivo en boletería"
)

// Selector de método de pago — lista de opciones tipo radio.
// Se usa en CompraTicketScreen.
@Composable
fun SelectorMetodoPago(
    metodoSeleccionado: String,
    onMetodoClick: (String) -> Unit
) {
    Column {
        Text("Método de pago", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
        Spacer(Modifier.height(8.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            metodosPago.forEach { metodo ->
                val seleccionado = metodo == metodoSeleccionado
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
                        .clickable { onMetodoClick(metodo) }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        metodo,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}