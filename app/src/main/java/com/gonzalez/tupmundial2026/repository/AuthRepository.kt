package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.data.Usuario
import com.gonzalez.tupmundial2026.data.UsuarioDao
import com.gonzalez.tupmundial2026.models.DTOLoginRequest
import com.gonzalez.tupmundial2026.models.DTORegistroRequest
import com.gonzalez.tupmundial2026.network.MundialApiService
import com.gonzalez.tupmundial2026.network.RetrofitClient

class AuthRepository(
    private val dao: UsuarioDao,
    private val api: MundialApiService
) {
    suspend fun registrar(nombre: String, email: String, password: String): Usuario {
        val response = api.registro(
            DTORegistroRequest(
                nombre = nombre.trim(),
                email = email.trim().lowercase(),
                password = password
            )
        )

        // CLAVE: guardar el token en RetrofitClient para que todas las
        // peticiones siguientes lo incluyan en el header Authorization
        RetrofitClient.token = response.token

        val usuario = Usuario(
            nombre = response.nombre,
            email = response.email,
            password = password,
            token = response.token
        )
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

        // CLAVE: guardar el token en RetrofitClient
        RetrofitClient.token = response.token

        val usuario = Usuario(
            nombre = response.nombre,
            email = response.email,
            password = password,
            token = response.token
        )
        try { dao.registrar(usuario) } catch (_: Exception) {}
        return usuario
    }

    fun logout() {
        // Nota: Al cerrar sesión se borra el token para que las próximas
        // peticiones no lleven un token de otra sesión
        RetrofitClient.token = null
    }
}