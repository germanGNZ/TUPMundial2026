@file:Suppress("unused")

package com.gonzalez.tupmundial2026.network


import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {
    private val json = Json { ignoreUnknownKeys = true }
    val api: MundialApiService by lazy {
        Retrofit.Builder()
            .baseUrl("s")
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(MundialApiService::class.java)
    }
}
