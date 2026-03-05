package com.kelompok3.bloomu.dailycheckin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CheckInViewModel : ViewModel() {
    // 1. STATE UI
    var currentStep by mutableIntStateOf(1) // Langkah 1 (Mood), 2 (Pertanyaan), 3 (Diary)
    var selectedMoodEmoji by mutableIntStateOf(3) // Skor mood keseluruhan (1-5)

    // 2. STATE PERTANYAAN (Randomized)
    var activeQuestions by mutableStateOf<List<Question>>(emptyList())
        private set

    // 3. STATE JAWABAN
    // Map untuk menyimpan jawaban user: Key = ID Pertanyaan, Value = Bobot/Skor
    var userAnswers by mutableStateOf<Map<Int, Int>>(emptyMap())

    // 4. STATE DIARY
    var diaryText by mutableStateOf("")

    init {
        // Mengacak pertanyaan tiap kali layar ini dibuka
        randomizeQuestions()
    }

    private fun randomizeQuestions() {
        // Mengambil 1 soal acak dari tiap domain (Mental, Fisik, Akademik)
        activeQuestions = listOf(
            CheckInBank.mentalQuestions.random(),
            CheckInBank.physicalQuestions.random(),
            CheckInBank.academicQuestions.random()
        )
    }

    // Fungsi pembantu untuk mengecek apakah semua pertanyaan sudah dijawab
    fun allQuestionsAnswered(): Boolean {
        return userAnswers.size == activeQuestions.size
    }

    fun submitCheckIn() {
        // Logika submit ke Supabase di sini
        // Hitung skor total per domain, dll.
    }
}
