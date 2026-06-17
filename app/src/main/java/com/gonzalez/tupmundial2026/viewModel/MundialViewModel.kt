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

    var partidosLista by mutableStateOf(emptyList<DTOPartidosLista>())
        private set
    var partidosDetalle by mutableStateOf<DTOPartidosDetalle?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun LlamarPartidos() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                partidosLista = repository.fetchPartidosLista()
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
}
