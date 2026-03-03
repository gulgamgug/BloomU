package com.kelompok3.bloomu.dailycheckin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

data class CheckInRequest(
    val user_id: String,
    val mood_score: Int,
    val mental_score: Int,
    val physical_score: Int,
    val academic_score: Int,
    val diary_note: String
)

class CheckInViewModel : ViewModel() {
    //state management
    var currentStep by mutableStateOf(1) // 1:mood, 2:questions, 3:diary
    var isLoading by mutableStateOf(false)
    //data inputan user
    var selectedMood by mutableStateOf(3)
    var diaryNote by mutableStateOf("")
    //pertanyaan yang randomized
    var activeQuestions by mutableStateOf<List<Question>>(emptyList())
    var userAnswers = mutableMapOf<Int, Int>()

    init {
        randomizeQuestions()
    }

    private fun randomizeQuestions() {
        activeQuestions = listOf(
            CheckInBank.mentalQuestions.random(),
            CheckInBank.physicalQuestions.random(),
            CheckInBank.academicQuestions.random()
        )
    }

    fun submitCheckIn(onSuccess: () -> Unit) {
        val userId = supabase.auth.currentUserOrNull()?.id ?: return
        isLoading = true
        viewModelScope.launch {
            try {
                val mentalScore = userAnswers[activeQuestions[0].id] ?: 0
                val physicalScore = userAnswers[activeQuestions[1].id] ?: 0
                val academicScore = userAnswers[activeQuestions[2].id] ?: 0

                val request = CheckInRequest(
                    user_id = userId,
                    mood_score = selectedMood,
                    mental_score = mentalScore,
                    physical_score = physicalScore,
                    academic_score = academicScore,
                    diary_note = diaryNote
                )
                supabase.postgrest["daily_checkins"].insert(request)
                onSuccess()
            } catch (e: Exception) {
                println("Error submitting check-in: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}