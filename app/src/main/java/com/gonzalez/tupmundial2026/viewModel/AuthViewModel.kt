package com.gonzalez.tupmundial2026.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonzalez.tupmundial2026.database.UsuarioDao
import com.gonzalez.tupmundial2026.models.Usuario
import com.gonzalez.tupmundial2026.utils.hashPassword
import kotlinx.coroutines.launch

class AuthViewModel(private val dao: UsuarioDao) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var usuarioLogueado by mutableStateOf<Usuario?>(null)
        private set

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Completá todos los campos"
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val hash = hashPassword(password)
                val usuario = dao.login(email.trim(), hash)
                if (usuario != null) {
                    usuarioLogueado = usuario
                    onSuccess()
                } else {
                    errorMessage = "Email o contraseña incorrectos"
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun registro(
        nombreUsuario: String,
        email: String,
        password: String,
        confirmarPassword: String,
        onSuccess: () -> Unit
    ) {
        // Validaciones
        if (nombreUsuario.isBlank() || email.isBlank() || password.isBlank()) {
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
                val existe = dao.emailExiste(email.trim())
                if (existe > 0) {
                    errorMessage = "Ese email ya está registrado"
                    return@launch
                }
                val nuevoUsuario = Usuario(
                    nombreUsuario = nombreUsuario.trim(),
                    email = email.trim(),
                    passwordHash = hashPassword(password)
                )
                dao.registrar(nuevoUsuario)
                onSuccess()
            } catch (e: Exception) {
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun limpiarError() { errorMessage = null }
}