package com.gonzalez.tupmundial2026.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class DatosComprador(
    val dni: String = "",
    val nombreCompleto: String = "",
    val email: String = "",
    val telefono: String = ""
)

private fun sinEmojis(texto: String): String =
    texto.filter { it.code in 32..126 || it.code > 160 && !Character.isSurrogate(it) }

fun validarDatosComprador(datos: DatosComprador): String? {
    if (datos.nombreCompleto.isBlank()) return "Ingresá tu nombre completo"
    if (datos.nombreCompleto.length < 3) return "El nombre debe tener al menos 3 caracteres"
    if (datos.dni.isBlank()) return "Ingresá tu DNI"
    if (!datos.dni.all { it.isDigit() } || datos.dni.length < 7 || datos.dni.length > 8)
        return "El DNI debe tener entre 7 y 8 números"
    if (datos.email.isBlank()) return "Ingresá tu correo electrónico"
    if (!datos.email.contains("@") || !datos.email.contains("."))
        return "Ingresá un correo electrónico válido"
    if (datos.telefono.isBlank()) return "Ingresá tu teléfono"
    if (!datos.telefono.all { it.isDigit() } || datos.telefono.length < 8)
        return "Ingresá un teléfono válido (solo números)"
    return null
}

@Composable
fun DatosCompradorForm(
    datos: DatosComprador,
    onDatosChange: (DatosComprador) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("Datos del comprador", color = Color.White.copy(alpha = 0.6f),
            fontSize = 12.sp, letterSpacing = 1.sp)

        OutlinedTextField(
            value = datos.nombreCompleto,
            onValueChange = {
                val filtrado = sinEmojis(it)
                if (filtrado.length <= 80) onDatosChange(datos.copy(nombreCompleto = filtrado))
            },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            colors = campoColores(),
            singleLine = true
        )

        OutlinedTextField(
            value = datos.dni,
            onValueChange = {
                val soloDigitos = it.filter { c -> c.isDigit() }
                if (soloDigitos.length <= 8) onDatosChange(datos.copy(dni = soloDigitos))
            },
            label = { Text("DNI") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = campoColores(),
            singleLine = true
        )

        OutlinedTextField(
            value = datos.email,
            onValueChange = {
                val filtrado = sinEmojis(it).filter { c -> !c.isWhitespace() }
                if (filtrado.length <= 100) onDatosChange(datos.copy(email = filtrado))
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = campoColores(),
            singleLine = true
        )

        OutlinedTextField(
            value = datos.telefono,
            onValueChange = {
                val soloDigitos = it.filter { c -> c.isDigit() }
                if (soloDigitos.length <= 15) onDatosChange(datos.copy(telefono = soloDigitos))
            },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = campoColores(),
            singleLine = true
        )
    }
}