package com.gonzalez.tupmundial2026.models

import kotlinx.serialization.Serializable

@Serializable
data class DTORegistroRequest(
    val nombre: String,
    val email: String,
    val password: String
)

@Serializable
data class DTOLoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class DTOAuthResponse(
    val token: String,
    val nombre: String,
    val email: String,
    val rol: String,
    val expira: String
)