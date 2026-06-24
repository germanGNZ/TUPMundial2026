package com.gonzalez.tupmundial2026.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gonzalez.tupmundial2026.ui.CompraTicketScreen
import com.gonzalez.tupmundial2026.ui.DetalleScreen
import com.gonzalez.tupmundial2026.ui.LoginScreen
import com.gonzalez.tupmundial2026.ui.MisTicketsScreen
import com.gonzalez.tupmundial2026.ui.PartidosScreen
import com.gonzalez.tupmundial2026.ui.RegisterScreen
import com.gonzalez.tupmundial2026.ui.TicketConfirmadoScreen
import com.gonzalez.tupmundial2026.viewModel.AuthViewModel
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel
import com.gonzalez.tupmundial2026.viewModel.TicketViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    mundialViewModel: MundialViewModel,
    authViewModel: AuthViewModel,
    ticketViewModel: TicketViewModel,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "login", modifier = modifier) {

        composable("login") {
            LaunchedEffect(authViewModel.estaLogueado) {
                if (authViewModel.estaLogueado)
                    navController.navigate("partidos") { popUpTo("login") { inclusive = true } }
            }
            LoginScreen(
                viewModel = authViewModel,
                onLoginExitoso = {
                    navController.navigate("partidos") { popUpTo("login") { inclusive = true } }
                },
                onIrARegistro = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegisterScreen(
                viewModel = authViewModel,
                onRegistroExitoso = {
                    navController.navigate("partidos") { popUpTo("login") { inclusive = true } }
                },
                onIrALogin = { navController.popBackStack() }
            )
        }

        composable("partidos") {
            LaunchedEffect(authViewModel.estaLogueado) {
                if (!authViewModel.estaLogueado)
                    navController.navigate("login") { popUpTo("partidos") { inclusive = true } }
            }
            PartidosScreen(
                viewModel = mundialViewModel,
                nombreUsuario = authViewModel.usuarioActual?.nombre,
                onPartidoClick = { id -> navController.navigate("detalle/$id") },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") { popUpTo("partidos") { inclusive = true } }
                },
                onMisTickets = { navController.navigate("mistickets") }
            )
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            LaunchedEffect(authViewModel.estaLogueado) {
                if (!authViewModel.estaLogueado)
                    navController.navigate("login") { popUpTo(0) { inclusive = true } }
            }
            val id = backStackEntry.arguments!!.getInt("id")
            DetalleScreen(
                id = id,
                viewModel = mundialViewModel,
                onBack = { navController.popBackStack() },
                onComprarEntrada = { navController.navigate("compra/$id") }
            )
        }

        composable(
            route = "compra/{partidoId}",
            arguments = listOf(navArgument("partidoId") { type = NavType.IntType })
        ) {
            val detalle = mundialViewModel.partidosDetalle
            val usuarioId = authViewModel.usuarioActual?.id ?: 0
            if (detalle != null) {
                CompraTicketScreen(
                    detalle = detalle,
                    usuarioId = usuarioId,
                    ticketViewModel = ticketViewModel,
                    onCompraExitosa = {
                        // NUEVO: navega a la pantalla de confirmación
                        navController.navigate("confirmacion") {
                            popUpTo("compra/{partidoId}") { inclusive = true }
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // confirmación de compra exitosa
        composable("confirmacion") {
            val ticket = ticketViewModel.ultimoTicketComprado
            if (ticket != null) {
                TicketConfirmadoScreen(
                    ticket = ticket,
                    onVerMisTickets = {
                        ticketViewModel.limpiarEstado()
                        navController.navigate("mistickets") {
                            popUpTo("partidos") { inclusive = false }
                        }
                    },
                    onVolver = {
                        ticketViewModel.limpiarEstado()
                        navController.navigate("partidos") {
                            popUpTo("partidos") { inclusive = false }
                        }
                    }
                )
            }
        }

        composable("mistickets") {
            val usuarioId = authViewModel.usuarioActual?.id ?: 0
            MisTicketsScreen(
                usuarioId = usuarioId,
                ticketViewModel = ticketViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}