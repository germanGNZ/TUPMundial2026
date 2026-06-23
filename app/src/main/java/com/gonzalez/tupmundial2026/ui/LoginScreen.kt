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

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().background(FondoOscuro)
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
        Box(modifier = Modifier.fillMaxWidth().height(3.dp)
            .background(Brush.horizontalGradient(listOf(Dorado, DoradoOscuro, Dorado))))

        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Iniciar Sesión", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            // Mensaje de error
            viewModel.errorMessage?.let { error ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF4E0000))
                        .padding(12.dp)
                ) {
                    Text(error, color = Color(0xFFEF9A9A), fontSize = 13.sp)
                }
            }

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

@Composable
fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Dorado,
    unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedLabelColor = Dorado,
    unfocusedLabelColor = Color.White.copy(alpha = 0.6f),
    cursorColor = Dorado
)