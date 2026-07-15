package com.gonzalez.tupmundial2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gonzalez.tupmundial2026.data.AppDatabase
import com.gonzalez.tupmundial2026.data.TokenDataStore
import com.gonzalez.tupmundial2026.network.RetrofitClient
import com.gonzalez.tupmundial2026.repository.AuthRepository
import com.gonzalez.tupmundial2026.repository.MundialRepository
import com.gonzalez.tupmundial2026.repository.TicketRepository
import com.gonzalez.tupmundial2026.ui.Dorado
import com.gonzalez.tupmundial2026.ui.navigation.AppNavigation
import com.gonzalez.tupmundial2026.ui.theme.TUPMundial2026Theme
import com.gonzalez.tupmundial2026.viewModel.AuthViewModel
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel
import com.gonzalez.tupmundial2026.viewModel.TicketViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getInstance(this)
        val tokenDataStore = TokenDataStore(this)  // NUEVO

        val authViewModel = AuthViewModel(
            AuthRepository(db.usuarioDao(), RetrofitClient.api, tokenDataStore)  // NUEVO: tokenDataStore
        )
        val mundialViewModel = MundialViewModel(MundialRepository(RetrofitClient.api))
        val ticketViewModel = TicketViewModel(
            TicketRepository(db.ticketDao(), RetrofitClient.api)
        )

        setContent {
            TUPMundial2026Theme {
                // Mientras se verifica si hay sesión guardada, muestra un spinner
                // para que la app no arranque en login ni en partidos antes de saber
                if (authViewModel.verificandoSesion) {
                    LaunchedEffect(Unit) {
                        authViewModel.restaurarSesion {}
                    }
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Dorado)
                    }
                } else {
                    val navController = rememberNavController()
                    AppNavigation(
                        modifier = Modifier.fillMaxSize(),
                        mundialViewModel = mundialViewModel,
                        authViewModel = authViewModel,
                        ticketViewModel = ticketViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

