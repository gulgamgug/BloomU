package com.kelompok3.bloomu.presentation.assessment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AssessmentResultViewModel : ViewModel() {
    
    var assessmentCard by mutableStateOf<AssessmentCard?>(null)
        private set

    val currentDate: String = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID")).format(Date())

    fun initialize(moodScore: Int, mentalScore: Int, physicalScore: Int, academicScore: Int) {
        val result = AssessmentContentProvider.getAssessmentResult(
            moodScore, mentalScore, physicalScore, academicScore
        )
        // Pilih satu microaction secara acak dari daftar yang tersedia
        val randomAction = result.microActions.random()
        assessmentCard = result.copy(microActions = listOf(randomAction))
    }
}
