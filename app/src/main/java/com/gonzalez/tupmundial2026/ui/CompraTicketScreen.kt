package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.ui.components.*
import com.gonzalez.tupmundial2026.utils.formatFecha
import com.gonzalez.tupmundial2026.utils.parsePrecio
import com.gonzalez.tupmundial2026.viewModel.TicketViewModel

@Composable
fun CompraTicketScreen(
    detalle: DTOPartidosDetalle,
    usuarioId: Int,
    emailUsuario: String,
    nombreUsuario: String,
    ticketViewModel: TicketViewModel,
    onCompraExitosa: () -> Unit,
    onBack: () -> Unit
) {
    var cantidad by remember { mutableIntStateOf(1) }
    var sectorSeleccionado by remember { mutableStateOf(sectoresDisponibles[1]) } // Platea por defecto
    var metodoSeleccionado by remember { mutableStateOf(metodosPago[0]) }
    var datosComprador by remember { mutableStateOf(DatosComprador()) }

    // Cálculo del desglose de precio según cantidad y sector elegido.
    // El precio que viene de la API es el precio "base" (equivalente a Platea);
    // el sector elegido aplica su multiplicador sobre ese valor.
    val precioBase = remember(detalle.precio) { parsePrecio(detalle.precio) }
    val precioUnitario = precioBase * sectorSeleccionado.multiplicador
    val subtotal = precioUnitario * cantidad
    val cargoServicio = subtotal * 0.10 // cargo de servicio del 10%
    val total = subtotal + cargoServicio

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        HeaderMundial(titulo = "Comprar Entrada", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card equipos
            EquiposVsCard(equipo1 = detalle.equipo1, equipo2 = detalle.equipo2)

            Column {
                DetalleCard("📅", "Fecha", formatFecha(detalle.fecha))
                DetalleCard("🏟", "Estadio", detalle.estadio)
                DetalleCard("🏆", "Grupo", detalle.grupo ?: "N/A")
            }

            // Selector de cantidad
            SelectorCantidad(
                cantidad = cantidad,
                onAumentar = { cantidad++ },
                onDisminuir = { cantidad-- }
            )

            // Selector de sector/ubicación — afecta el precio final
            SelectorSector(
                sectorSeleccionado = sectorSeleccionado,
                onSectorClick = { sectorSeleccionado = it }
            )

            // Datos obligatorios del comprador
            DatosCompradorForm(
                datos = datosComprador,
                onDatosChange = { datosComprador = it; ticketViewModel.limpiarError() }
            )

            // Método de pago
            SelectorMetodoPago(
                metodoSeleccionado = metodoSeleccionado,
                onMetodoClick = { metodoSeleccionado = it }
            )

            // Resumen con desglose de precio (se recalcula con cantidad/sector)
            ResumenCompra(
                precioUnitario = precioUnitario,
                cantidad = cantidad,
                subtotal = subtotal,
                cargoServicio = cargoServicio,
                total = total
            )

            // Mensaje de error (validación de datos o error de compra)
            ticketViewModel.errorMessage?.let {
                MensajeError(it)
            }

            Button(
                onClick = {
                    ticketViewModel.comprar(
                        usuarioId = usuarioId,
                        emailUsuario = emailUsuario,
                        nombreUsuario = nombreUsuario,
                        detalle = detalle,
                        cantidadEntradas = cantidad,
                        sector = sectorSeleccionado.nombre,
                        metodoPago = metodoSeleccionado,
                        datosComprador = datosComprador,
                        subtotal = subtotal,
                        cargoServicio = cargoServicio,
                        total = total,
                        onExito = onCompraExitosa
                    )
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                enabled = !ticketViewModel.isLoading
            ) {
                if (ticketViewModel.isLoading)
                    CircularProgressIndicator(color = FondoOscuro, modifier = Modifier.size(20.dp))
                else
                    Text(
                        "Confirmar compra ($cantidad entrada${if (cantidad > 1) "s" else ""})",
                        color = FondoOscuro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}