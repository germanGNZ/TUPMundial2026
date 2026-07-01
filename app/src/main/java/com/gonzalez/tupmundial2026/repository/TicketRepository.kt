package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.data.Ticket
import com.gonzalez.tupmundial2026.data.TicketDao
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.ui.components.DatosComprador

class TicketRepository(private val dao: TicketDao) {

    suspend fun comprar(
        usuarioId: Int,
        detalle: DTOPartidosDetalle,
        cantidadEntradas: Int,
        sector: String,
        metodoPago: String,
        datosComprador: DatosComprador,
        subtotal: Double,
        cargoServicio: Double,
        total: Double
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
            grupo = detalle.grupo ?: "",
            precio = detalle.precio,
            cantidadEntradas = cantidadEntradas,
            sector = sector,
            metodoPago = metodoPago,
            dniComprador = datosComprador.dni,
            nombreComprador = datosComprador.nombreCompleto,
            emailComprador = datosComprador.email,
            telefonoComprador = datosComprador.telefono,
            subtotal = subtotal,
            cargoServicio = cargoServicio,
            total = total
        )
        dao.comprar(ticket)
        return ticket
    }

    suspend fun obtenerMisTickets(usuarioId: Int): List<Ticket> {
        return dao.obtenerMisTickets(usuarioId)
    }
}