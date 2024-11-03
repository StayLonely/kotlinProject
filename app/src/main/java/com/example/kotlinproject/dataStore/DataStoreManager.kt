package com.example.kotlinproject.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.kotlinproject.utils.Util.isFilter

import kotlinx.coroutines.flow.map


private val  Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStore_settings")
class DataStoreManager(val context: Context) {

    suspend fun saveSettings(settingsData: SettingsData){
        context.dataStore.edit { pref->
            pref[stringPreferencesKey("status")] = settingsData.status
            pref[stringPreferencesKey("contentRating")] = settingsData.contentRating
            pref[stringPreferencesKey("tags")] = settingsData.tags.joinToString(",")
        }
        isFilter.value = true


    }
    fun getSettings() = context.dataStore.data.map { pref->
        return@map SettingsData(
            pref[stringPreferencesKey("status")] ?: "",
            pref[stringPreferencesKey("contentRating")] ?: "",
            pref[stringPreferencesKey("tags")]?.split(",")?.toMutableList() ?:mutableListOf()
        )
    }

    suspend fun resetSettings(){
        context.dataStore.edit { pref->
            pref[stringPreferencesKey("status")] = ""
            pref[stringPreferencesKey("contentRating")] = ""
            pref[stringPreferencesKey("tags")] = ""
        }
        isFilter.value = false
    }
}