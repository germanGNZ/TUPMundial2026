package com.gonzalez.tupmundial2026.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonzalez.tupmundial2026.data.Ticket
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.repository.TicketRepository
import kotlinx.coroutines.launch

class TicketViewModel(private val repository: TicketRepository) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var compraExitosa by mutableStateOf(false)
        private set

    var misTickets by mutableStateOf(emptyList<Ticket>())
        private set

    fun comprar(
        usuarioId: Int,
        detalle: DTOPartidosDetalle,
        cantidadEntradas: Int,
        onExito: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            compraExitosa = false
            try {
                repository.comprar(usuarioId, detalle, cantidadEntradas)
                compraExitosa = true
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
    fun limpiarEstado() { compraExitosa = false; errorMessage = null }
}