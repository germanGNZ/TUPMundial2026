package com.gonzalez.tupmundial2026.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {

    // Guarda un ticket nuevo después de confirmar la compra
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun comprar(ticket: Ticket)

    // Devuelve todos los tickets del usuario logueado
    @Query("SELECT * FROM tickets WHERE usuarioId = :usuarioId ORDER BY fechaCompra DESC")
    suspend fun obtenerMisTickets(usuarioId: Int): List<Ticket>

    // Verifica si el usuario ya compró entrada para ese partido
    @Query("SELECT COUNT(*) FROM tickets WHERE usuarioId = :usuarioId AND partidoId = :partidoId")
    suspend fun yaCompro(usuarioId: Int, partidoId: Int): Int
}