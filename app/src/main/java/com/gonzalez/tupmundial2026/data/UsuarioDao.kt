package com.gonzalez.tupmundial2026.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// DAO (Data Access Object) — define las operaciones disponibles
// sobre la tabla "usuarios".

@Dao
interface UsuarioDao {

    // Inserta un usuario nuevo. Si el email ya existe (por el Index unique)
    // Room lanza una excepción que capturamos en el Repository.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registrar(usuario: Usuario)

    // Busca un usuario por email Y password. Si no existe o la contraseña
    // no coincide, devuelve null.
    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): Usuario?

    // Verifica si un email ya está registrado (para mostrar error antes
    // de intentar insertar).
    @Query("SELECT COUNT(*) FROM usuarios WHERE email = :email")
    suspend fun emailExiste(email: String): Int
}