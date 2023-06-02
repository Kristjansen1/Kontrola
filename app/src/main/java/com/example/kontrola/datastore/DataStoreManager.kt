package com.example.kontrola.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kontrola.applog
import kotlinx.coroutines.flow.first

class DataStoreManager(context: Context) {

    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "settings"
    )
    private val dataStore = context.settingsDataStore

    suspend fun write(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit{ settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val settings = dataStore.data.first()
        applog("settings ${settings}")
        return settings[dataStoreKey]
    }

}