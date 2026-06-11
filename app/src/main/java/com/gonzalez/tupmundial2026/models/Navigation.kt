package com.gonzalez.tupmundial2026.models

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gonzalez.tupmundial2026.ui.PartidosScreen
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier, viewModel: MundialViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "partidos", modifier = modifier) {
        composable("partidos") {
            PartidosScreen(viewModel = viewModel)
        }
        // Agrega más pantallas (detalle de partido, etc.)
    }
}
