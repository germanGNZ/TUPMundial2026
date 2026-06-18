package com.gonzalez.tupmundial2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gonzalez.tupmundial2026.ui.theme.TUPMundial2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabase.getInstance(this)
        val authViewModel = AuthViewModel(db.usuarioDao())
        val mundialViewModel = MundialViewModel(MundialRepository())

        setContent {
            TUPMundial2026Theme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "login") {

                    composable("login") {
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginExitoso = {
                                navController.navigate("lista") {
                                    popUpTo("login") { inclusive = true } // no puede volver al login
                                }
                            },
                            onIrARegistro = { navController.navigate("registro") }
                        )
                    }

                    composable("registro") {
                        RegistroScreen(
                            viewModel = authViewModel,
                            onRegistroExitoso = {
                                navController.navigate("lista") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onIrALogin = { navController.popBackStack() }
                        )
                    }

                    composable("lista") {
                        PartidosScreen(
                            viewModel = mundialViewModel,
                            onPartidoClick = { id -> navController.navigate("detalle/$id") }
                        )
                    }

                    composable("detalle/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toInt() ?: return@composable
                        PartidoDetalleScreen(
                            id = id,
                            viewModel = mundialViewModel,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TUPMundial2026Theme {
        Greeting("Android")
    }
}

