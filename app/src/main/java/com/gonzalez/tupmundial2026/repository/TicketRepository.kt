package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.data.Ticket
import com.gonzalez.tupmundial2026.data.TicketDao
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle

class TicketRepository(private val dao: TicketDao) {

    suspend fun comprar(
        usuarioId: Int,
        detalle: DTOPartidosDetalle,
        cantidadEntradas: Int
    ): Ticket {
        // Verifica si el usuario ya compró para este partido
        val yaCompro = dao.yaCompro(usuarioId, detalle.id) > 0
        if (yaCompro) {
            throw Exception("Ya tenés una entrada comprada para este partido")
        }
        if (cantidadEntradas < 1) {
            throw Exception("La cantidad de entradas debe ser al menos 1")
        }

        val ticket = Ticket(
            usuarioId = usuarioId,
            partidoId = detalle.id,
            equipo1 = detalle.equipo1,
            equipo2 = detalle.equipo2,
            fecha = detalle.fecha,
            estadio = detalle.estadio,
            grupo = detalle.grupo,
            precio = detalle.precio,
            cantidadEntradas = cantidadEntradas
        )
        dao.comprar(ticket)
        return ticket
    }

    suspend fun obtenerMisTickets(usuarioId: Int): List<Ticket> {
        return dao.obtenerMisTickets(usuarioId)
    }
}