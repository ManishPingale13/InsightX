package com.example.insightx.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("auth_prefs")

class DataStoreRepository @Inject constructor(
    private val context: Context
) {
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

    suspend fun getUser(): String? {
        return context.dataStore.data.first()[USER_NAME_KEY]
    }

    suspend fun getPass(): String? {
        return context.dataStore.data.first()[USER_PASS_KEY]
    }
}