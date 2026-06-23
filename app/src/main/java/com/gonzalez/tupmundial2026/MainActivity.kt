package com.gonzalez.tupmundial2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gonzalez.tupmundial2026.data.AppDatabase
import com.gonzalez.tupmundial2026.network.RetrofitClient
import com.gonzalez.tupmundial2026.repository.AuthRepository
import com.gonzalez.tupmundial2026.repository.MundialRepository
import com.gonzalez.tupmundial2026.ui.navigation.AppNavigation
import com.gonzalez.tupmundial2026.ui.theme.TUPMundial2026Theme
import com.gonzalez.tupmundial2026.viewModel.AuthViewModel
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Room — base de datos local para usuarios
        val db = AppDatabase.getInstance(this)
        val authViewModel = AuthViewModel(AuthRepository(db.usuarioDao()))

        // Retrofit — API de partidos
        val mundialViewModel = MundialViewModel(MundialRepository(RetrofitClient.api))

        setContent {
            TUPMundial2026Theme {
                val navController = rememberNavController()
                AppNavigation(
                    modifier = Modifier.fillMaxSize(),
                    mundialViewModel = mundialViewModel,
                    authViewModel = authViewModel,
                    navController = navController
                )
            }
        }
    }
}

