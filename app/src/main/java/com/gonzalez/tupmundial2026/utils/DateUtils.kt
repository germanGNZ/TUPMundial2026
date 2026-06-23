package com.gonzalez.tupmundial2026.utils
// CORRECCIÓN: package cambiado de "Utils" a "utils" (minúscula).
// En Android/Kotlin los packages deben ser en minúsculas.
// La carpeta también debe renombrarse de "Utils" a "utils".

fun formatFecha(fecha: String): String {
    return try {
        val partes = fecha.split("T")
        val dia = partes[0].split("-").reversed().joinToString("/")
        val hora = partes[1].substring(0, 5)
        "$dia $hora hs"
    } catch (_: Exception) {
        fecha
    }
}