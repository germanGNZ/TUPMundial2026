package com.gonzalez.tupmundial2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gonzalez.tupmundial2026.ui.theme.TUPMundial2026Theme
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel
import com.gonzalez.tupmundial2026.repository.MundialRepository
import com.gonzalez.tupmundial2026.network.MundialApiService
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configuración básica de Retrofit (ajusta la URL base según tu API)
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://tu-api-base-url.com/")  // ← CAMBIA ESTO
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val api = retrofit.create(MundialApiService::class.java)
        val repository = MundialRepository(api)

        setContent {
            TUPMundial2026Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel { MundialViewModel(repository) }
                    )
                }
            }
        }
    }
}