package com.kelompok3.bloomu.presentation.mission

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kelompok3.bloomu.presentation.mission.components.MissionCategoryMode

data class DailyMission(
    val id: Int,
    val title: String,
    val description: String,
    val estimation: String,
    val isFinished: Boolean
)

enum class MissionScreen {
    MAIN, // Halaman MissionPage utama yang sudah ada
    SELECT,
    DETAILS
}

class MissionViewModel : ViewModel() {
    var currentScreen by mutableStateOf(MissionScreen.MAIN)
        private set

    var selectedCategoryMode by mutableStateOf<MissionCategoryMode?>(null)
        private set

    // Daftar mode yang sudah di-subscribe oleh user
    var subscribedModes by mutableStateOf(setOf<MissionCategoryMode>())
        private set

    // Filter yang sedang aktif
    var activeFilters by mutableStateOf(setOf<MissionCategoryMode>())
        private set

    var streakCount by mutableStateOf(4)
        private set

    // Misi harian statis (contoh)
    var staticMissions by mutableStateOf(
        listOf(
            DailyMission(1, "Rapikan tempat tidurmu", "Langkah kecil yang bikin pikiran lebih teratur.", "3-5 menit", false),
            DailyMission(2, "Lari pagi", "Gerakan ringan yang bantu melepaskan stres.", "30-60 menit", false)
        )
    )
        private set

    fun navigateTo(screen: MissionScreen) {
        currentScreen = screen
    }

    fun selectCategoryMode(mode: MissionCategoryMode) {
        selectedCategoryMode = mode
        navigateTo(MissionScreen.DETAILS)
    }

    fun subscribeMode(mode: MissionCategoryMode) {
        subscribedModes = subscribedModes + mode
        navigateTo(MissionScreen.MAIN)
    }

    fun unsubscribeMode(mode: MissionCategoryMode) {
        subscribedModes = subscribedModes - mode
        activeFilters = activeFilters - mode
    }

    fun toggleFilter(mode: MissionCategoryMode) {
        activeFilters = if (activeFilters.contains(mode)) {
            activeFilters - mode
        } else {
            activeFilters + mode
        }
    }

    fun toggleMission(missionId: Int) {
        staticMissions = staticMissions.map {
            if (it.id == missionId) it.copy(isFinished = !it.isFinished) else it
        }
    }
}
