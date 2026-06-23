package com.gonzalez.tupmundial2026.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonzalez.tupmundial2026.data.Usuario
import com.gonzalez.tupmundial2026.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Usuario actualmente logueado. Null = no hay sesión activa.
    var usuarioActual by mutableStateOf<Usuario?>(null)
        private set

    // Calculado a partir de usuarioActual, lo usa Navigation para
    // proteger las rutas.
    val estaLogueado: Boolean
        get() = usuarioActual != null

    fun login(email: String, password: String, onExito: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Completá todos los campos"
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                usuarioActual = repository.login(email, password)
                onExito()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun registrar(
        nombre: String,
        email: String,
        password: String,
        confirmarPassword: String,
        onExito: () -> Unit
    ) {
        if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
            errorMessage = "Completá todos los campos"
            return
        }
        if (!email.contains("@")) {
            errorMessage = "El email no es válido"
            return
        }
        if (password.length < 6) {
            errorMessage = "La contraseña debe tener al menos 6 caracteres"
            return
        }
        if (password != confirmarPassword) {
            errorMessage = "Las contraseñas no coinciden"
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                usuarioActual = repository.registrar(nombre, email, password)
                onExito()
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun logout() {
        usuarioActual = null
        errorMessage = null
    }

    fun limpiarError() {
        errorMessage = null
    }
}