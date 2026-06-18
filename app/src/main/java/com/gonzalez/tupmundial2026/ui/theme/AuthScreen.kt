package com.gonzalez.tupmundial2026.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonzalez.tupmundial2026.viewModel.AuthViewModel

private val VerdeOscuro = Color(0xFF1B5E20)
private val VerdeMedio = Color(0xFF2E7D32)
private val Dorado = Color(0xFFFFC107)
private val DoradoOscuro = Color(0xFFF57F17)
private val FondoOscuro = Color(0xFF121212)
private val TarjetaFondo = Color(0xFF1E1E1E)

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(FondoOscuro),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                .padding(horizontal = 20.dp, vertical = 36.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⚽", fontSize = 48.sp)
                Spacer(Modifier.height(8.dp))
                Text("Mundial 2026", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
                Text("Iniciá sesión para continuar", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado))))

        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text("Iniciar Sesión", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            // Error
            viewModel.errorMessage?.let {
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF4E0000)).padding(12.dp)
                ) {
                    Text(it, color = Color(0xFFEF9A9A), fontSize = 13.sp)
                }
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; viewModel.limpiarError() },
                label = { Text("Email", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; viewModel.limpiarError() },
                label = { Text("Contraseña", color = Color.White.copy(alpha = 0.6f)) },
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
                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) CircularProgressIndicator(color = FondoOscuro, modifier = Modifier.size(20.dp))
                else Text("Ingresar", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            TextButton(onClick = onIrARegistro, modifier = Modifier.fillMaxWidth()) {
                Text("¿No tenés cuenta? ", color = Color.White.copy(alpha = 0.6f))
                Text("Registrate", color = Dorado, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RegistroScreen(
    viewModel: AuthViewModel,
    onRegistroExitoso: () -> Unit,
    onIrALogin: () -> Unit
) {
    var nombreUsuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(FondoOscuro),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(VerdeOscuro, VerdeMedio)))
                .padding(horizontal = 20.dp, vertical = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("⚽", fontSize = 40.sp)
                Spacer(Modifier.height(6.dp))
                Text("Crear cuenta", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado))))

        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            viewModel.errorMessage?.let {
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF4E0000)).padding(12.dp)
                ) {
                    Text(it, color = Color(0xFFEF9A9A), fontSize = 13.sp)
                }
            }

            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it; viewModel.limpiarError() },
                label = { Text("Nombre de usuario", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; viewModel.limpiarError() },
                label = { Text("Email", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; viewModel.limpiarError() },
                label = { Text("Contraseña", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = campoColores(),
                singleLine = true
            )

            OutlinedTextField(
                value = confirmarPassword,
                onValueChange = { confirmarPassword = it; viewModel.limpiarError() },
                label = { Text("Confirmar contraseña", color = Color.White.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = campoColores(),
                singleLine = true
            )

            Button(
                onClick = { viewModel.registro(nombreUsuario, email, password, confirmarPassword, onRegistroExitoso) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Dorado),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) CircularProgressIndicator(color = FondoOscuro, modifier = Modifier.size(20.dp))
                else Text("Crear cuenta", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            TextButton(onClick = onIrALogin, modifier = Modifier.fillMaxWidth()) {
                Text("¿Ya tenés cuenta? ", color = Color.White.copy(alpha = 0.6f))
                Text("Iniciá sesión", color = Dorado, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Dorado,
    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    cursorColor = Dorado
)