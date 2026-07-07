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

private fun sinEmojis(texto: String): String =
    texto.filter { it.code in 32..126 || it.code > 160 && !Character.isSurrogate(it) }

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegistroExitoso: () -> Unit,
    onIrALogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(FondoOscuro)) {
        HeaderAuth(titulo = "Crear cuenta", subtitulo = "Registrate para ver los partidos")

        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            viewModel.errorMessage?.let { MensajeError(it) }

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    val filtrado = sinEmojis(it)
                    if (filtrado.length <= 50) { nombre = filtrado; viewModel.limpiarError() }
                },
                label = { Text("Nombre") },
                supportingText = { Text("${nombre.length}/50", color = Color.White.copy(alpha = 0.4f)) },
                modifier = Modifier.fillMaxWidth(),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    val filtrado = sinEmojis(it).filter { c -> !c.isWhitespace() }
                    if (filtrado.length <= 100) { email = filtrado; viewModel.limpiarError() }
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    val filtrado = sinEmojis(it).filter { c -> !c.isWhitespace() }
                    if (filtrado.length <= 30) { password = filtrado; viewModel.limpiarError() }
                },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = confirmarPassword,
                onValueChange = {
                    val filtrado = sinEmojis(it).filter { c -> !c.isWhitespace() }
                    if (filtrado.length <= 30) { confirmarPassword = filtrado; viewModel.limpiarError() }
                },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = campoColores(),
                singleLine = true
            )

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = { viewModel.registrar(nombre, email, password, confirmarPassword, onRegistroExitoso) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading)
                    CircularProgressIndicator(color = FondoOscuro, modifier = Modifier.size(20.dp))
                else
                    Text("Crear cuenta", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            TextButton(onClick = onIrALogin, modifier = Modifier.fillMaxWidth()) {
                Text("¿Ya tenés cuenta? ", color = Color.White.copy(alpha = 0.6f))
                Text("Iniciá sesión", color = Dorado, fontWeight = FontWeight.Bold)
            }
        }
    }
}