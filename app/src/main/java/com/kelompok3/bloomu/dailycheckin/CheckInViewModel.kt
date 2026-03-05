package com.kelompok3.bloomu.dailycheckin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyCheckIn(
    @SerialName("user_id") val userId: String,
    @SerialName("mood_score") val moodScore: Int,
    @SerialName("mental_score") val mentalScore: Int,
    @SerialName("physical_score") val physicalScore: Int,
    @SerialName("academic_score") val academicScore: Int,
    @SerialName("diary_note") val diaryNote: String?,
    @SerialName("selected_questions") val selectedQuestions: Map<Int, Int>
)

class CheckInViewModel : ViewModel() {
    // 1. STATE UI
    var currentStep by mutableIntStateOf(1)
    var selectedMoodEmoji by mutableIntStateOf(3)
    var isLoading by mutableStateOf(false)

    // 2. STATE PERTANYAAN
    var activeQuestions by mutableStateOf<List<Question>>(emptyList())
        private set

    // 3. STATE JAWABAN
    var userAnswers by mutableStateOf<Map<Int, Int>>(emptyMap())

    // 4. STATE DIARY
    var diaryText by mutableStateOf("")

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

    fun allQuestionsAnswered(): Boolean {
        return userAnswers.size == activeQuestions.size
    }

    fun submitCheckIn(onSuccess: () -> Unit) {
        val user = supabase.auth.currentUserOrNull() ?: return
        
        viewModelScope.launch {
            isLoading = true
            try {
                // Hitung skor per domain dari userAnswers
                val mentalScore = activeQuestions.filter { it.domain == Domain.MENTAL }
                    .sumOf { userAnswers[it.id] ?: 0 }
                val physicalScore = activeQuestions.filter { it.domain == Domain.FISIK }
                    .sumOf { userAnswers[it.id] ?: 0 }
                val academicScore = activeQuestions.filter { it.domain == Domain.AKADEMIK }
                    .sumOf { userAnswers[it.id] ?: 0 }

                val data = DailyCheckIn(
                    userId = user.id,
                    moodScore = selectedMoodEmoji,
                    mentalScore = mentalScore,
                    physicalScore = physicalScore,
                    academicScore = academicScore,
                    diaryNote = diaryText.ifBlank { null },
                    selectedQuestions = userAnswers
                )

                supabase.postgrest["daily_checkins"].insert(data)
                
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                // Nanti bisa tambahkan error handling seperti Toast
            } finally {
                isLoading = false
            }
        }
    }
}
