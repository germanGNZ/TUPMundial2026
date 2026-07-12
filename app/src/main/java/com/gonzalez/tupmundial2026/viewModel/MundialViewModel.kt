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

    companion object {
        const val POR_PAGINA = 10   // partidos por página
    }

    // Nota: Lista acumulada de partidos (se agrega al ir cargando más páginas)
    var partidosLista by mutableStateOf(emptyList<DTOPartidosLista>())
        private set

    var partidosDetalle by mutableStateOf<DTOPartidosDetalle?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Nota: true solo cuando se está cargando más páginas (no la primera)
    var isCargandoMas by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var textoBusqueda by mutableStateOf("")
        private set

    // Paginado
    private var paginaActual = 1
    var hayMasPaginas by mutableStateOf(false)
        private set

    var paginaInfo by mutableStateOf("")   // "Página 1 de 11 (104 partidos)"
        private set

    fun LlamarPartidos() {
        // Nota: Primera carga: resetea la lista y arranca desde página 1
        paginaActual = 1
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val respuesta = repository.fetchPartidosPaginados(
                    pagina = 1,
                    porPagina = POR_PAGINA
                )
                partidosLista = respuesta.datos
                hayMasPaginas = paginaActual < respuesta.totalPaginas
                paginaInfo = "Página 1 de ${respuesta.totalPaginas} (${respuesta.total} partidos)"
            } catch (e: Exception) {
                errorMessage = "Error al cargar los partidos: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun cargarMasPaginas() {
        if (isCargandoMas || !hayMasPaginas) return
        viewModelScope.launch {
            isCargandoMas = true
            errorMessage = null
            try {
                paginaActual++
                val respuesta = repository.fetchPartidosPaginados(
                    pagina = paginaActual,
                    porPagina = POR_PAGINA
                )
                // Agregar los nuevos partidos a la lista existente
                partidosLista = partidosLista + respuesta.datos
                hayMasPaginas = paginaActual < respuesta.totalPaginas
                paginaInfo = "Página $paginaActual de ${respuesta.totalPaginas} (${respuesta.total} partidos)"
            } catch (e: Exception) {
                paginaActual--   // revertir si falló
                errorMessage = "Error al cargar más partidos: ${e.message}"
            } finally {
                isCargandoMas = false
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
    }

    fun limpiarBusqueda() {
        textoBusqueda = ""
    }
}
