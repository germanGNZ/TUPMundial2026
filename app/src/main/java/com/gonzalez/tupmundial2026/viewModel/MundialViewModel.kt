package com.gonzalez.tupmundial2026.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import com.gonzalez.tupmundial2026.repository.MundialRepository
import kotlinx.coroutines.launch

class MundialViewModel(private val repository: MundialRepository) : ViewModel() {

    private var _todosLosPartidos = emptyList<DTOPartidosLista>()

    var partidosLista by mutableStateOf(emptyList<DTOPartidosLista>())
        private set
    var partidosDetalle by mutableStateOf<DTOPartidosDetalle?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var textoBusqueda by mutableStateOf("")
        private set

    fun LlamarPartidos() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                _todosLosPartidos = repository.fetchPartidosLista()
                aplicarFiltros()
            } catch (e: Exception) {
                errorMessage = "Error al cargar los partidos: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun LlamarDetalle(id: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                partidosDetalle = repository.fetchPartidoDetalle(id)
            } catch (e: Exception) {
                errorMessage = "Error al cargar el detalle: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun buscar(texto: String) {
        textoBusqueda = texto
        aplicarFiltros()
    }

    fun limpiarBusqueda() {
        textoBusqueda = ""
        aplicarFiltros()
    }

    private fun aplicarFiltros() {
        val texto = textoBusqueda.trim().lowercase()
        partidosLista = if (texto.isEmpty()) {
            _todosLosPartidos
        } else {
            _todosLosPartidos.filter { p ->
                p.equipo1.lowercase().contains(texto) ||
                        p.equipo2.lowercase().contains(texto) ||
                        (p.grupo?.lowercase()?.contains(texto) == true) ||
                        p.estadio.lowercase().contains(texto)
            }
        }
    }
}
