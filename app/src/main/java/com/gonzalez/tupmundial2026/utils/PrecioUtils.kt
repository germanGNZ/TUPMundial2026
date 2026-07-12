package com.gonzalez.tupmundial2026.utils

import java.util.Locale

// Formatea un valor numérico como precio en dólares con 2 decimales.
// Ejemplo: 150.0 → "$150.00"
fun formatPrecio(valor: Double): String {
    return "$" + String.format("%.2f", valor)
}