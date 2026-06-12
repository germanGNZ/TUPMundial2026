package com.gonzalez.tupmundial2026.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gonzalez.tupmundial2026.ui.PartidoDetalleScreen
import com.gonzalez.tupmundial2026.ui.PartidosScreen
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    viewModel: MundialViewModel,
    navController: NavHostController  // CORRECCIÓN: usamos el que viene de afuera, no creamos uno nuevo
) {
    NavHost(navController = navController, startDestination = "partidos", modifier = modifier) {

        composable("partidos") {
            PartidosScreen(
                viewModel = viewModel,
                onPartidoClick = { id ->
                    navController.navigate("detalle/$id")
                }
            )
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable
            PartidoDetalleScreen(
                id = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

