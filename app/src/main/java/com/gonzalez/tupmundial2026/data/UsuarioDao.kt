package com.gonzalez.tupmundial2026.database

import androidx.room.*
import com.gonzalez.tupmundial2026.models.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun registrar(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email AND passwordHash = :hash LIMIT 1")
    suspend fun login(email: String, hash: String): Usuario?

    @Query("SELECT COUNT(*) FROM usuarios WHERE email = :email")
    suspend fun emailExiste(email: String): Int
}