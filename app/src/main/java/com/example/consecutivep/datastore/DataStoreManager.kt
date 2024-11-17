package com.example.consecutivep.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.consecutivep.utils.LocalUtils.isFilter

import kotlinx.coroutines.flow.map


private val  Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStore_settings")
class DataStoreManager(val context: Context) {

    suspend fun saveSettings(settingsData: SettingsData){
        context.dataStore.edit { pref->
            pref[stringPreferencesKey("type")] = settingsData.type
            pref[stringPreferencesKey("status")] = settingsData.status
        }
        isFilter.value = true


    }
    fun getSettings() = context.dataStore.data.map { pref->
        return@map SettingsData(
            pref[stringPreferencesKey("type")] ?: "",
            pref[stringPreferencesKey("status")] ?: "",
        )
    }

    suspend fun resetSettings(){
        context.dataStore.edit { pref->
            pref[stringPreferencesKey("type")] = ""
            pref[stringPreferencesKey("status")] = ""
        }
        isFilter.value = false
    }
}