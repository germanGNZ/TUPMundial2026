package com.gonzalez.tupmundial2026.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// se agrega Ticket::class a las entidades y se sube
// la versión a 2. Esto es obligatorio cuando se agrega una tabla nueva
// a una base de datos Room que ya existe en el celular.
// fallbackToDestructiveMigration() borra y recrea la base si detecta
// que la versión cambió — para este proyecto es aceptable.
// se agregaron columnas nuevas a Ticket (sector, método
// de pago, datos del comprador y desglose de precio), por eso se sube
// la versión de 2 a 3. fallbackToDestructiveMigration() se encarga de
// recrear la base con la nueva estructura.

@Database(
    entities = [Usuario::class, Ticket::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mundial_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}