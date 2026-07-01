package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.ui.components.HeaderAuth
import com.gonzalez.tupmundial2026.ui.components.MensajeError
import com.gonzalez.tupmundial2026.ui.components.campoColores
import com.gonzalez.tupmundial2026.viewModel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        HeaderAuth(titulo = "Mundial 2026", subtitulo = "Iniciá sesión para continuar")

        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Iniciar Sesión", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            viewModel.errorMessage?.let { MensajeError(it) }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; viewModel.limpiarError() },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; viewModel.limpiarError() },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = campoColores(),
                singleLine = true
            )

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = { viewModel.login(email, password, onLoginExitoso) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading)
                    CircularProgressIndicator(color = FondoOscuro, modifier = Modifier.size(20.dp))
                else
                    Text("Ingresar", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            TextButton(onClick = onIrARegistro, modifier = Modifier.fillMaxWidth()) {
                Text("¿No tenés cuenta? ", color = Color.White.copy(alpha = 0.6f))
                Text("Registrate", color = Dorado, fontWeight = FontWeight.Bold)
            }
        }
    }
}