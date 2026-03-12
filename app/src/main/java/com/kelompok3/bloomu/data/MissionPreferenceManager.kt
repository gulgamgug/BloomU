package com.kelompok3.bloomu.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "mission_prefs")

class MissionPreferenceManager(private val context: Context) {

    companion object {
        val SUBSCRIBED_MODES = stringSetPreferencesKey("subscribed_modes")
        val FINISHED_MISSION_IDS = stringSetPreferencesKey("finished_mission_ids")
        val LAST_RESET_DATE = stringPreferencesKey("last_reset_date")
    }

    // Mendapatkan set kategori yang diikuti
    val subscribedModes: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[SUBSCRIBED_MODES] ?: emptySet()
    }

    // Mendapatkan set ID misi yang sudah selesai
    val finishedMissionIds: Flow<Set<String>> = context.dataStore.data.map { prefs ->
        prefs[FINISHED_MISSION_IDS] ?: emptySet()
    }

    // Mendapatkan tanggal terakhir reset
    val lastResetDate: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[LAST_RESET_DATE]
    }

    // Simpan kategori baru
    suspend fun saveSubscribedModes(modes: Set<String>) {
        context.dataStore.edit { prefs ->
            prefs[SUBSCRIBED_MODES] = modes
        }
    }

    // Simpan status misi selesai
    suspend fun saveFinishedMissionIds(ids: Set<String>) {
        context.dataStore.edit { prefs ->
            prefs[FINISHED_MISSION_IDS] = ids
        }
    }

    // Simpan tanggal hari ini (saat reset)
    suspend fun saveLastResetDate(date: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_RESET_DATE] = date
        }
    }

    // Reset progres harian
    suspend fun clearDailyProgress() {
        context.dataStore.edit { prefs ->
            prefs[FINISHED_MISSION_IDS] = emptySet()
        }
    }
}
