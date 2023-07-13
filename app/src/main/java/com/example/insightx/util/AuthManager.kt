package com.example.insightx.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore("auth_prefs")
class AuthManager(private val context: Context) {


    companion object {
        val USER_NAME_KEY = stringPreferencesKey("USER_AGE")
        val USER_PASS_KEY = stringPreferencesKey("USER_PASS")
    }

    suspend fun storeUser(name: String, pass: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME_KEY] = name
            prefs[USER_PASS_KEY] = pass
        }
    }

    val credsFlow: Flow<Map<String?, String?>> = context.dataStore.data.map { prefs ->
        val headerMap = mutableMapOf<String?, String?>()
        headerMap["USER"] = prefs[USER_NAME_KEY]
        headerMap["PASS"] = prefs[USER_PASS_KEY]
        headerMap
    }

    val passFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_PASS_KEY]
    }

}