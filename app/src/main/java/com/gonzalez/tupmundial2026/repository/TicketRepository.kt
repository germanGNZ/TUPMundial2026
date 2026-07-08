package com.gonzalez.tupmundial2026.repository

import com.gonzalez.tupmundial2026.data.Ticket
import com.gonzalez.tupmundial2026.data.TicketDao
import com.gonzalez.tupmundial2026.models.DTOPartidosDetalle
import com.gonzalez.tupmundial2026.models.DTOTicketRequest
import com.gonzalez.tupmundial2026.network.MundialApiService
import com.gonzalez.tupmundial2026.ui.components.DatosComprador

class TicketRepository(
    private val dao: TicketDao,
    private val api: MundialApiService   // NUEVO: recibe el servicio de red
) {

    suspend fun comprar(
        usuarioId: Int,
        emailUsuario: String,          // NUEVO: necesario para enviar a la API
        nombreUsuario: String,         // NUEVO: necesario para enviar a la API
        detalle: DTOPartidosDetalle,
        cantidadEntradas: Int,
        sector: String,
        metodoPago: String,
        datosComprador: DatosComprador,
        subtotal: Double,
        cargoServicio: Double,
        total: Double
    ): Ticket {
        // 1. Verificar que no haya comprado ya para este partido
        val yaCompro = dao.yaCompro(usuarioId, detalle.id) > 0
        if (yaCompro) throw Exception("Ya tenés una entrada comprada para este partido")
        if (cantidadEntradas < 1) throw Exception("La cantidad de entradas debe ser al menos 1")

        // 2. Guardar en Room (base de datos local)
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

        // 3. Enviar a MongoDB vía API (en segundo plano, no bloquea si falla)
        // Si la API no está disponible, el ticket igual quedó guardado en Room
        // y el usuario no pierde su compra.
        try {
            api.crearTicket(
                DTOTicketRequest(
                    partidoId = detalle.id.toString(),
                    numeroPartido = detalle.id,
                    equipo1 = detalle.equipo1,
                    equipo2 = detalle.equipo2,
                    fechaPartido = detalle.fecha,
                    estadio = detalle.estadio,
                    grupo = detalle.grupo ?: "",
                    nombreUsuario = nombreUsuario,
                    emailComprador = datosComprador.email.ifBlank { emailUsuario },
                    dniComprador = datosComprador.dni,
                    telefonoComprador = datosComprador.telefono,
                    sector = sector,
                    cantidadEntradas = cantidadEntradas,
                    metodoPago = metodoPago,
                    precio = detalle.precio,
                    subtotal = subtotal,
                    cargoServicio = cargoServicio,
                    total = total
                )
            )
        } catch (e: Exception) {
            // Si la API falla (sin red, servidor caído), no lanzamos error al usuario.
            // El ticket ya está guardado localmente en Room.
            // Se puede agregar un log o un flag de "pendiente de sincronizar" si se necesita.
        }

        return ticket
    }

    suspend fun obtenerMisTickets(usuarioId: Int): List<Ticket> {
        return dao.obtenerMisTickets(usuarioId)
    }
}