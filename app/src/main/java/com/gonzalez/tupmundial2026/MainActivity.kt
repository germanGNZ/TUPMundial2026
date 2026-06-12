package com.gonzalez.tupmundial2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gonzalez.tupmundial2026.models.AppNavigation
import com.gonzalez.tupmundial2026.ui.theme.TUPMundial2026Theme
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel
import com.gonzalez.tupmundial2026.repository.MundialRepository
//import com.gonzalez.tupmundial2026.network.MundialApiService
import com.gonzalez.tupmundial2026.network.RetrofitClient
/*import com.gonzalez.tupmundial2026.network.RetrofitClient.api
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val retrofit = RetrofitClient.api

        val repository = MundialRepository(retrofit)

        setContent {
            TUPMundial2026Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = androidx.navigation.compose.rememberNavController()
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel { MundialViewModel(repository) }
                    )
                }
            }
        }
    }
}
