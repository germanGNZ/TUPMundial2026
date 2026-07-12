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

    // detalle.precio ya es Double — no necesita parseo
    val precioBase = detalle.precio
    val precioUnitario = precioBase * sectorSeleccionado.multiplicador
    val subtotal = precioUnitario * cantidad
    val cargoServicio = subtotal * 0.10
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
            EquiposVsCard(equipo1 = detalle.equipo1, equipo2 = detalle.equipo2)

            Column {
                DetalleCard("📅", "Fecha", formatFecha(detalle.fecha))
                DetalleCard("🏟", "Estadio", detalle.estadio)
                DetalleCard("🏆", "Grupo", detalle.grupo ?: "N/A")
            }

            SelectorCantidad(
                cantidad = cantidad,
                onAumentar = { cantidad++ },
                onDisminuir = { cantidad-- }
            )

            SelectorSector(
                sectorSeleccionado = sectorSeleccionado,
                onSectorClick = { sectorSeleccionado = it }
            )

            DatosCompradorForm(
                datos = datosComprador,
                onDatosChange = { datosComprador = it; ticketViewModel.limpiarError() }
            )

            SelectorMetodoPago(
                metodoSeleccionado = metodoSeleccionado,
                onMetodoClick = { metodoSeleccionado = it }
            )

            ResumenCompra(
                precioUnitario = precioUnitario,
                cantidad = cantidad,
                subtotal = subtotal,
                cargoServicio = cargoServicio,
                total = total
            )

            ticketViewModel.errorMessage?.let { MensajeError(it) }

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