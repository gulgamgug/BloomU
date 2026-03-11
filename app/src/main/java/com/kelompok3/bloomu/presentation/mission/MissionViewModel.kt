package com.kelompok3.bloomu.presentation.mission

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class DailyMission(
    val id: Int,
    val title: String,
    val description: String,
    val estimation: String,
    val isFinished: Boolean
)

class MissionViewModel : ViewModel() {
    var streakCount by mutableStateOf(4)
        private set

    var categories by mutableStateOf(listOf("Kebiasaan kecil", "Energi"))
        private set

    var selectedCategory by mutableStateOf("Kebiasaan kecil")
        private set

    var missions by mutableStateOf(
        listOf(
            DailyMission(
                id = 1,
                title = "Rapikan tempat tidurmu",
                description = "Langkah kecil yang bikin pikiran lebih teratur, mood lebih positif, dan siap menghadapi aktivitas seharian.",
                estimation = "Estimasi waktu: 3-5 menit",
                isFinished = false
            ),
            DailyMission(
                id = 2,
                title = "Lari pagi",
                description = "Gerakan ringan yang bantu melepaskan stres, meningkatkan energi, dan bikin mood lebih fresh!.",
                estimation = "Estimasi waktu: 30-60 menit",
                isFinished = false
            ),
            DailyMission(
                id = 3,
                title = "8 gelas air putih setiap hari",
                description = "Hidrasi yang cukup bantu tubuh lebih segar dan pikiran lebih fokus!",
                estimation = "Estimasi waktu: 3-5 menit",
                isFinished = true
            )
        )
    )
        private set

    fun onCategorySelected(category: String) {
        selectedCategory = category
        // Nantinya bisa ditambahkan logika untuk filter misi berdasarkan kategori
    }

    fun toggleMission(missionId: Int) {
        missions = missions.map {
            if (it.id == missionId) it.copy(isFinished = !it.isFinished) else it
        }
    }
}
