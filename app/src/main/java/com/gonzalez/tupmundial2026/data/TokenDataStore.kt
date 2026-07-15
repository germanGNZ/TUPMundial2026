package com.gonzalez.tupmundial2026.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

// Crea una sola instancia de DataStore para toda la app (no se puede crear más de una)
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sesion")

class TokenDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
    }

    // Lee el token guardado. Devuelve null si no hay ninguno.
    suspend fun getToken(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[TOKEN_KEY] }
            .firstOrNull()
    }

    // Guarda el token (sobreescribe si ya había uno)
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    // Borra el token (se llama al hacer logout)
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}