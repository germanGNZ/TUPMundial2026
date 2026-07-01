package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.gonzalez.tupmundial2026.ui.TarjetaFondo
import com.gonzalez.tupmundial2026.utils.formatPrecio

// Resumen con el desglose del precio final: subtotal (precio unitario x cantidad),
// cargo de servicio y total a pagar. Se usa en CompraTicketScreen y en la
// pantalla de confirmación.
@Composable
fun ResumenCompra(
    precioUnitario: Double,
    cantidad: Int,
    subtotal: Double,
    cargoServicio: Double,
    total: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TarjetaFondo)
            .padding(16.dp)
    ) {
        Text("Resumen de tu compra", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp, letterSpacing = 1.sp)
        Spacer(Modifier.height(10.dp))

        FilaResumen("Precio por entrada", formatPrecio(precioUnitario))
        FilaResumen("Cantidad", "x$cantidad")
        FilaResumen("Subtotal", formatPrecio(subtotal))
        FilaResumen("Cargo por servicio", formatPrecio(cargoServicio))

        Spacer(Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.White.copy(alpha = 0.1f)))
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total a pagar", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(formatPrecio(total), color = Dorado, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun FilaResumen(label: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
        Text(valor, color = Color.White, fontSize = 13.sp)
    }
}