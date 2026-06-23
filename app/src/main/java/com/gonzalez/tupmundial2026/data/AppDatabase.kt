package com.gonzalez.tupmundial2026.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Configuración de la base de datos local.
// exportSchema = false porque no necesitamos historial de migraciones
// para este proyecto.

@Database(entities = [Usuario::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton: una sola instancia de la base de datos en toda la app.
        // @Volatile asegura que los cambios sean visibles entre hilos.
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mundial_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}