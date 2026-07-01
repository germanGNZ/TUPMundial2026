package com.gonzalez.tupmundial2026.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonzalez.tupmundial2026.data.Ticket
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.repository.TicketRepository
import com.gonzalez.tupmundial2026.ui.components.DatosComprador
import com.gonzalez.tupmundial2026.ui.components.validarDatosComprador
import kotlinx.coroutines.launch

class TicketViewModel(private val repository: TicketRepository) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var misTickets by mutableStateOf(emptyList<Ticket>())
        private set

    // guarda el último ticket comprado para mostrarlo en la confirmación
    var ultimoTicketComprado by mutableStateOf<Ticket?>(null)
        private set

    fun comprar(
        usuarioId: Int,
        detalle: DTOPartidosDetalle,
        cantidadEntradas: Int,
        sector: String,
        metodoPago: String,
        datosComprador: DatosComprador,
        subtotal: Double,
        cargoServicio: Double,
        total: Double,
        onExito: () -> Unit
    ) {
        // Valida los datos del comprador antes de llamar al repositorio
        val errorValidacion = validarDatosComprador(datosComprador)
        if (errorValidacion != null) {
            errorMessage = errorValidacion
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val ticket = repository.comprar(
                    usuarioId = usuarioId,
                    detalle = detalle,
                    cantidadEntradas = cantidadEntradas,
                    sector = sector,
                    metodoPago = metodoPago,
                    datosComprador = datosComprador,
                    subtotal = subtotal,
                    cargoServicio = cargoServicio,
                    total = total
                )
                ultimoTicketComprado = ticket
                onExito()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun cargarMisTickets(usuarioId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                misTickets = repository.obtenerMisTickets(usuarioId)
            } catch (e: Exception) {
                errorMessage = "Error al cargar los tickets: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun limpiarError() { errorMessage = null }
    fun limpiarEstado() { ultimoTicketComprado = null; errorMessage = null }
}