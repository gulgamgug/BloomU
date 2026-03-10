@file:OptIn(ExperimentalTime::class)

package com.kelompok3.bloomu.presentation.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
data class MoodEntry(
    @SerialName("mood_score") val moodScore: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("diary_note") val diaryNote: String? = null
)

class CalendarViewModel : ViewModel() {
    // buat nampung data mood sebulan (tanggal -> data mood)
    var monthlyMoodData by mutableStateOf<Map<Int, MoodEntry>>(emptyMap())
        private set

    // state buat kontrol UI
    var selectedMonth by mutableStateOf(kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).month)
    var selectedYear by mutableStateOf(kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year)
    var selectedDay by mutableStateOf(kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).dayOfMonth)

    init {
        fetchMonthlyMoodData()
    }

    // fungsi narik data dari supabase sesuai bulan yang dipilih
    fun fetchMonthlyMoodData() {
        val user = supabase.auth.currentUserOrNull() ?: return

        // Reset data lama supaya tidak muncul di bulan lain saat transisi
        monthlyMoodData = emptyMap()

        viewModelScope.launch {
            try {
                val tz = TimeZone.currentSystemDefault()
                
                // batesan awal bulan
                val monthStart = LocalDateTime(selectedYear, selectedMonth, 1, 0, 0, 0, 0)
                val monthStartIso = monthStart.toInstant(tz).toString()
                
                // batesan awal bulan depan
                val nextMonth = if (selectedMonth == Month.DECEMBER) Month.JANUARY else Month(selectedMonth.number + 1)
                val nextYear = if (selectedMonth == Month.DECEMBER) selectedYear + 1 else selectedYear
                val nextMonthStart = LocalDateTime(nextYear, nextMonth, 1, 0, 0, 0, 0)
                val nextMonthStartIso = nextMonthStart.toInstant(tz).toString()

                val response = supabase.postgrest["daily_checkins"].select(columns = Columns.list("mood_score", "created_at", "diary_note")) {
                    filter {
                        eq("user_id", user.id)
                        gte("created_at", monthStartIso)
                        lt("created_at", nextMonthStartIso)
                    }
                }

                val entries = response.decodeList<MoodEntry>()

                // mapping tanggal ke object mood
                val moodMap = mutableMapOf<Int, MoodEntry>()
                entries.forEach { entry ->
                    val entryDate = Instant.parse(entry.createdAt).toLocalDateTime(tz)
                    // Pastikan data benar-benar milik bulan & tahun yang dipilih (double check)
                    if (entryDate.month == selectedMonth && entryDate.year == selectedYear) {
                        moodMap[entryDate.dayOfMonth] = entry
                    }
                }
                monthlyMoodData = moodMap
                } catch (e: Exception) {
                e.printStackTrace()
                }
                }
                }

                // hitung jumlah tiap mood buat grafik
                fun getMoodDistribution(): List<Int> {
                val dist = MutableList(5) { 0 } // index 0: score 1, dst
                monthlyMoodData.values.forEach {
                if (it.moodScore in 1..5) {
                dist[it.moodScore - 1]++
                }
                }
                return dist
                }

                // buat tau jumlah hari dalam sebulan
                fun getDaysCount(): Int {
                return when (selectedMonth) {
                Month.FEBRUARY -> if (selectedYear % 4 == 0 && (selectedYear % 100 != 0 || selectedYear % 400 == 0)) 29 else 28
                Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
                else -> 31
                }
                }
                }

