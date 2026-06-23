package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.data.Usuario
import com.gonzalez.tupmundial2026.data.UsuarioDao

// Repository de autenticación. Recibe el DAO por constructor y
// expone funciones suspend para registrar e iniciar sesión.
// Toda la lógica de validación de negocio vive acá, no en el ViewModel.

class AuthRepository(private val dao: UsuarioDao) {

    suspend fun registrar(nombre: String, email: String, password: String): Usuario {
        val emailOcupado = dao.emailExiste(email.trim()) > 0
        if (emailOcupado) {
            throw Exception("Ese email ya está registrado")
        }
        val nuevoUsuario = Usuario(
            nombre = nombre.trim(),
            email = email.trim(),
            password = password
        )
        dao.registrar(nuevoUsuario)
        return nuevoUsuario
    }

    suspend fun login(email: String, password: String): Usuario {
        return dao.login(email.trim(), password)
            ?: throw Exception("Email o contraseña incorrectos")
    }
}