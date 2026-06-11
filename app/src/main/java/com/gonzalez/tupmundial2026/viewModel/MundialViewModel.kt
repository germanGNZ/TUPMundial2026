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

class MundialViewModel (private val repository: MundialRepository) : ViewModel() {

    var partidosLista by mutableStateOf(emptyList<DTOPartidosLista>())
    var isLoading by mutableStateOf(false)
        private set

    private fun List<DTOPartidosLista>.add(fetchPartidosLista: List<DTOPartidosLista>) {
        TODO("Not yet implemented")
    }

    // Reemplaza la función add por esto:
    fun LlamarPartidos() {
        viewModelScope.launch {
            isLoading = true
            try {
                partidosLista = repository.fetchPartidosLista()  // ← Asignación directa
            } catch (e: Exception) {
                // Manejar error (por ejemplo, mostrar mensaje)
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    var partidosDetalle by mutableStateOf(emptyList<DTOPartidosDetalle>())
}