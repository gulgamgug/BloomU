package com.kelompok3.bloomu.presentation.mission

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.data.MissionPreferenceManager
import com.kelompok3.bloomu.presentation.mission.components.MissionCategoryMode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate

data class DailyMission(
    val id: Int,
    val title: String,
    val description: String,
    val estimation: String,
    val isFinished: Boolean
)

enum class MissionScreen {
    MAIN, SELECT, DETAILS
}

class MissionViewModel(application: Application) : AndroidViewModel(application) {
    private val prefManager = MissionPreferenceManager(application)

    var currentScreen by mutableStateOf(MissionScreen.MAIN)
        private set

    var selectedCategoryMode by mutableStateOf<MissionCategoryMode?>(null)
        private set

    var subscribedModes by mutableStateOf(setOf<MissionCategoryMode>())
        private set

    var activeFilters by mutableStateOf(setOf<MissionCategoryMode>())
        private set

    var allMissions by mutableStateOf(listOf<DailyMission>())
        private set

    init {
        loadDataFromPrefs()
    }

    private fun loadDataFromPrefs() {
        viewModelScope.launch {
            val today = LocalDate.now().toString()
            val lastReset = prefManager.lastResetDate.first()
            
            // 1. Ambil kategori yang diikuti
            val savedModes = prefManager.subscribedModes.first().mapNotNull { modeName ->
                try { MissionCategoryMode.valueOf(modeName) } catch (e: Exception) { null }
            }.toSet()
            subscribedModes = savedModes

            // 2. Bangun daftar misi dari kategori tersebut
            var initialMissions = savedModes.flatMap { getMissionDataFromMode(it) }

            // 3. Cek Reset Harian
            if (lastReset != today) {
                // Hari baru: progres nol
                prefManager.clearDailyProgress()
                prefManager.saveLastResetDate(today)
            } else {
                // Hari yang sama: muat progres yang tersimpan
                val finishedIds = prefManager.finishedMissionIds.first().map { it.toInt() }.toSet()
                initialMissions = initialMissions.map { 
                    if (finishedIds.contains(it.id)) it.copy(isFinished = true) else it 
                }
            }
            
            allMissions = initialMissions
        }
    }

    fun navigateTo(screen: MissionScreen) {
        currentScreen = screen
    }

    fun selectCategoryMode(mode: MissionCategoryMode) {
        selectedCategoryMode = mode
        navigateTo(MissionScreen.DETAILS)
    }

    fun subscribeMode(mode: MissionCategoryMode) {
        if (!subscribedModes.contains(mode)) {
            val newModes = subscribedModes + mode
            subscribedModes = newModes
            
            val newMissions = getMissionDataFromMode(mode)
            allMissions = allMissions + newMissions
            
            // Simpan ke DataStore
            viewModelScope.launch {
                prefManager.saveSubscribedModes(newModes.map { it.name }.toSet())
            }
        }
        navigateTo(MissionScreen.MAIN)
    }

    fun unsubscribeMode(mode: MissionCategoryMode) {
        val newModes = subscribedModes - mode
        subscribedModes = newModes
        activeFilters = activeFilters - mode
        
        val missionsToRemove = getMissionDataFromMode(mode).map { it.id }.toSet()
        allMissions = allMissions.filterNot { missionsToRemove.contains(it.id) }
        
        // Simpan ke DataStore
        viewModelScope.launch {
            prefManager.saveSubscribedModes(newModes.map { it.name }.toSet())
            // Juga bersihkan ID misi yang selesai jika kategorinya dihapus
            val remainingFinishedIds = allMissions.filter { it.isFinished }.map { it.id.toString() }.toSet()
            prefManager.saveFinishedMissionIds(remainingFinishedIds)
        }
    }

    fun toggleFilter(mode: MissionCategoryMode) {
        activeFilters = if (activeFilters.contains(mode)) activeFilters - mode else activeFilters + mode
    }

    fun toggleMission(missionId: Int) {
        allMissions = allMissions.map {
            if (it.id == missionId) it.copy(isFinished = !it.isFinished) else it
        }
        
        // Simpan progres ke DataStore
        viewModelScope.launch {
            val finishedIds = allMissions.filter { it.isFinished }.map { it.id.toString() }.toSet()
            prefManager.saveFinishedMissionIds(finishedIds)
        }
    }
}
