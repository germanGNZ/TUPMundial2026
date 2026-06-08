package com.gonzalez.tupmundial2026.network

import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosLista
import retrofit2.http.GET

interface MundialApiService {
    @GET("PartidosLista")
    suspend fun getPartidosLista(): List<DTOPartidosLista>

    @GET("PartidosDetalle")
    suspend fun getPartidosDetalle(): List<DTOPartidosDetalle>

}