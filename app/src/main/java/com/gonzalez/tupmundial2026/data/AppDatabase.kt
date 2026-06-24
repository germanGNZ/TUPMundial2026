package com.gonzalez.tupmundial2026.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// MODIFICADO: se agrega Ticket::class a las entidades y se sube
// la versión a 2. Esto es obligatorio cuando se agrega una tabla nueva
// a una base de datos Room que ya existe en el celular.
// fallbackToDestructiveMigration() borra y recrea la base si detecta
// que la versión cambió — para este proyecto es aceptable.

@Database(
    entities = [Usuario::class, Ticket::class],
    version = 2,
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