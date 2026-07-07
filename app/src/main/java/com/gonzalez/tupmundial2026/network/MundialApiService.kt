package com.gonzalez.tupmundial2026.network

import com.gonzalez.tupmundial2026.models.DTOAuthResponse
import com.gonzalez.tupmundial2026.models.DTOLoginRequest
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOPartidosRespuesta
import com.gonzalez.tupmundial2026.models.DTORegistroRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MundialApiService {

    // Lista paginada con filtros opcionales
    @GET("api/partido")
    suspend fun getPartidosLista(
        @Query("pagina") pagina: Int = 1,
        @Query("porPagina") porPagina: Int = 104
    ): DTOPartidosRespuesta

    // Detalle por numeroPartido (1-104)
    @GET("api/partido/numero/{numero}")
    suspend fun getPartidoDetalle(
        @Path("numero") id: Int
    ): DTOPartidosDetalle

    @POST("api/auth/registro")
    suspend fun registro(@Body body: DTORegistroRequest): DTOAuthResponse

    @POST("api/auth/login")
    suspend fun login(@Body body: DTOLoginRequest): DTOAuthResponse
}


