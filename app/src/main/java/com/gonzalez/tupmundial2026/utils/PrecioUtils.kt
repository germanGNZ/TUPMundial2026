package com.gonzalez.tupmundial2026.utils

import java.util.Locale

fun parsePrecio(precio: Double): Double {
    val match = Regex(pattern = "[0-9]+(,[0-9]+)?").find(input = precio.toString()) ?: return 0.0
    return match.value.replace(oldValue = ",", newValue = ".").toDoubleOrNull() ?: 0.0
}

fun formatPrecio(valor: Double): String {
    return "$" + String.format(Locale.US, "%.2f", valor)
}