package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.data.Usuario
import com.gonzalez.tupmundial2026.data.UsuarioDao
import com.gonzalez.tupmundial2026.models.DTOLoginRequest
import com.gonzalez.tupmundial2026.models.DTORegistroRequest
import com.gonzalez.tupmundial2026.network.MundialApiService

class AuthRepository(
    private val dao: UsuarioDao,
    private val api: MundialApiService
) {
    suspend fun registrar(nombre: String, email: String, password: String): Usuario {
        // Llamar a la API — si el email ya existe, la API devuelve 422
        // y Retrofit lanza una excepción que captura el ViewModel
        val response = api.registro(
            DTORegistroRequest(
                nombre = nombre.trim(),
                email = email.trim().lowercase(),
                password = password
            )
        )

        // Guardar en Room como caché local (para uso offline)
        val usuario = Usuario(
            nombre = response.nombre,
            email = response.email,
            password = password,
            token = response.token
        )

        // Insertar o reemplazar si ya existe
        try { dao.registrar(usuario) } catch (_: Exception) {}

        return usuario
    }

    suspend fun login(email: String, password: String): Usuario {
        val response = api.login(
            DTOLoginRequest(
                email = email.trim().lowercase(),
                password = password
            )
        )

        val usuario = Usuario(
            nombre = response.nombre,
            email = response.email,
            password = password,
            token = response.token
        )

        try { dao.registrar(usuario) } catch (_: Exception) {}

        return usuario
    }
}