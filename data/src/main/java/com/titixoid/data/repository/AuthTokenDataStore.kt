package com.titixoid.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthTokenDataStore(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
        val EMAIL_KEY = stringPreferencesKey("auth_email")
    }

    val authTokenFlow: Flow<String?> = dataStore.data.map { it[TOKEN_KEY] }
    val authEmailFlow: Flow<String?> = dataStore.data.map { it[EMAIL_KEY] }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit { it[EMAIL_KEY] = email }
    }

    suspend fun clearAuthData() {
        dataStore.edit {
            it.remove(TOKEN_KEY)
            it.remove(EMAIL_KEY)
        }
    }
}