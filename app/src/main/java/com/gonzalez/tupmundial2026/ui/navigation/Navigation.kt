package com.gonzalez.tupmundial2026.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gonzalez.tupmundial2026.ui.DetalleScreen
import com.gonzalez.tupmundial2026.ui.LoginScreen
import com.gonzalez.tupmundial2026.ui.PartidosScreen
import com.gonzalez.tupmundial2026.ui.RegisterScreen
import com.gonzalez.tupmundial2026.viewModel.AuthViewModel
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    mundialViewModel: MundialViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {

        composable("login") {
            // Si ya hay sesión, saltar directo a partidos
            LaunchedEffect(authViewModel.estaLogueado) {
                if (authViewModel.estaLogueado) {
                    navController.navigate("partidos") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
            LoginScreen(
                viewModel = authViewModel,
                onLoginExitoso = {
                    navController.navigate("partidos") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegistroExitoso = {
                    navController.navigate("partidos") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onIrALogin = { navController.popBackStack() }
            )
        }

        composable("partidos") {
            // Protección: si no hay sesión, volver al login
            LaunchedEffect(authViewModel.estaLogueado) {
                if (!authViewModel.estaLogueado) {
                    navController.navigate("login") {
                        popUpTo("partidos") { inclusive = true }
                    }
                }
            }
            PartidosScreen(
                viewModel = mundialViewModel,
                nombreUsuario = authViewModel.usuarioActual?.nombre,
                onPartidoClick = { id -> navController.navigate("detalle/$id") },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("partidos") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            // Protección: si no hay sesión, volver al login
            LaunchedEffect(authViewModel.estaLogueado) {
                if (!authViewModel.estaLogueado) {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
            val id = backStackEntry.arguments!!.getInt("id")
            DetalleScreen(
                id = id,
                viewModel = mundialViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}