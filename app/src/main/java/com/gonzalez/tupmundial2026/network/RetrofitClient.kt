package com.gonzalez.tupmundial2026.network

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {
    private val json = Json { ignoreUnknownKeys = true }
    private const val BASE_URL = "http://192.168.1.7:5123/"

    // Nota: El token se guarda acá cuando el usuario hace login o registro.
    // Se limpia a null cuando el usuario cierra sesión.
    var token: String? = null

    // Nota: Interceptor que agrega el header Authorization en CADA petición
    // si hay un token guardado. Esto es lo que la API verifica con [Authorize].
    private val authInterceptor = Interceptor { chain ->
        val originalRequest: Request = chain.request()

        val requestBuilder = originalRequest.newBuilder()

        token?.let {
            // Formato estándar JWT: "Bearer eyJhbGci..."
            requestBuilder.header("Authorization", "Bearer $it")
        }

        chain.proceed(requestBuilder.build())
    }

    // Nota: OkHttpClient con el interceptor aplicado a todas las peticiones
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val api: MundialApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)          // Nota: usa el cliente con el interceptor
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(MundialApiService::class.java)
    }
}