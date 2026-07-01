package com.gonzalez.tupmundial2026.utils

// El precio viene de la API como texto suelto (ej: "$150", "150 USD", "150").
// Esta función saca el primer número que encuentra para poder usarlo en cálculos.
fun parsePrecio(precio: String): Double {
    val match = Regex("[0-9]+([.,][0-9]+)?").find(precio) ?: return 0.0
    return match.value.replace(",", ".").toDoubleOrNull() ?: 0.0
}

// Formatea un valor numérico como precio en dólares con 2 decimales (ej: 150.0 -> "$150.00")
fun formatPrecio(valor: Double): String {
    return "$" + String.format("%.2f", valor)
}