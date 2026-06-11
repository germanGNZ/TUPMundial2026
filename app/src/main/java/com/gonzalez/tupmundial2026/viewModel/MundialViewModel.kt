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

@Suppress("unused", "UnusedReceiverParameter")
class MundialViewModel (private val repository: MundialRepository) : ViewModel() {

    var partidosLista by mutableStateOf(emptyList<DTOPartidosLista>())
    var isLoading by mutableStateOf(false)
        private set

    private fun List<DTOPartidosLista>.add(fetchPartidosLista: List<DTOPartidosLista>) {
        TODO("Not yet implemented")
    }

    @Suppress("FunctionName")
    fun LlamarPartidos() {
        viewModelScope.launch {
            isLoading = true

            try {
                partidosLista.add(repository.fetchPartidosLista())
            } catch (e: Exception) { /* error */
            }

            isLoading = false
        }
    }

    var partidosDetalle by mutableStateOf(emptyList<DTOPartidosDetalle>())
}
