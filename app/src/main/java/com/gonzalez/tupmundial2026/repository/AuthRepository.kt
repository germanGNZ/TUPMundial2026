package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.data.TokenDataStore
import com.gonzalez.tupmundial2026.data.Usuario
import com.gonzalez.tupmundial2026.data.UsuarioDao
import com.gonzalez.tupmundial2026.models.DTOLoginRequest
import com.gonzalez.tupmundial2026.models.DTORegistroRequest
import com.gonzalez.tupmundial2026.network.MundialApiService
import com.gonzalez.tupmundial2026.network.RetrofitClient

class AuthRepository(
    private val dao: UsuarioDao,
    private val api: MundialApiService,
    private val tokenDataStore: TokenDataStore
) {
    suspend fun registrar(nombre: String, email: String, password: String): Usuario {
        try {
            val response = api.registro(
                DTORegistroRequest(
                    nombre = nombre.trim(),
                    email = email.trim().lowercase(),
                    password = password
                )
            )

            RetrofitClient.token = response.token
            tokenDataStore.saveToken(response.token)

            val usuario = Usuario(
                nombre = response.nombre,
                email = response.email,
                password = password,
                token = response.token
            )
            try { dao.registrar(usuario) } catch (_: Exception) {}
            return usuario

        } catch (e: retrofit2.HttpException) {
            // Extrae el mensaje real del body en vez de mostrar "HTTP 4xx ..."
            val errorBody = e.response()?.errorBody()?.string()
            val mensaje = try {
                org.json.JSONObject(errorBody ?: "").getString("message")
            } catch (_: Exception) {
                "Error al registrarse. Intentá de nuevo."
            }
            throw Exception(mensaje)
        }
    }

    suspend fun login(email: String, password: String): Usuario {
        try {
            val response = api.login(
                DTOLoginRequest(
                    email = email.trim().lowercase(),
                    password = password
                )
            )

            RetrofitClient.token = response.token
            tokenDataStore.saveToken(response.token)

            val usuario = Usuario(
                nombre = response.nombre,
                email = response.email,
                password = password,
                token = response.token
            )
            try { dao.registrar(usuario) } catch (_: Exception) {}
            return usuario

        } catch (e: retrofit2.HttpException) {

            val errorBody = e.response()?.errorBody()?.string()
            val mensaje = try {
                org.json.JSONObject(errorBody ?: "").getString("message")
            } catch (_: Exception) {
                "Correo electrónico o contraseña incorrectos"
            }
            throw Exception(mensaje)
        }
    }

    suspend fun logout() {
        RetrofitClient.token = null
        tokenDataStore.clearToken()
    }

    suspend fun getTokenGuardado(): String? {
        return tokenDataStore.getToken()
    }
}