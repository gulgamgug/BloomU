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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class MoodEntry(
    @SerialName("mood_score") val moodScore: Int,
    @SerialName("created_at") val createdAt: String
)

class CalendarViewModel : ViewModel() {
    // buat nampung data mood sebulan
    var monthlyMoodData by mutableStateOf<Map<Int, Float>>(emptyMap())
        private set

    init {
        fetchMonthlyMoodData()
    }

    // fungsi ambil data mood sebulan penuh
    @OptIn(ExperimentalTime::class)
    fun fetchMonthlyMoodData() {
        val user = supabase.auth.currentUserOrNull() ?: return

        viewModelScope.launch {
            try {
                val tz = TimeZone.currentSystemDefault()
                val now = Clock.System.now().toLocalDateTime(tz)
                
                // set awal bulan ini
                val monthStart = LocalDateTime(now.year, now.month, 1, 0, 0, 0, 0)
                val monthStartIso = monthStart.toInstant(tz).toString()
                
                // set awal bulan depan buat batesan query
                val nextMonthNumber = if (now.monthNumber == 12) 1 else now.monthNumber + 1
                val nextYear = if (now.monthNumber == 12) now.year + 1 else now.year
                val nextMonthStart = LocalDateTime(nextYear, kotlinx.datetime.Month(nextMonthNumber), 1, 0, 0, 0, 0)
                val nextMonthStartIso = nextMonthStart.toInstant(tz).toString()

                // ambil data mood dari supabase
                val response = supabase.postgrest["daily_checkins"].select(columns = Columns.list("mood_score", "created_at")) {
                    filter {
                        eq("user_id", user.id)
                        gte("created_at", monthStartIso)
                        lt("created_at", nextMonthStartIso)
                    }
                }

                val entries = response.decodeList<MoodEntry>()
                
                // pindahin data ke map (tanggal -> skor mood)
                val moodMap = mutableMapOf<Int, Float>()
                entries.forEach { entry ->
                    val entryDate = Instant.parse(entry.createdAt).toLocalDateTime(tz)
                    // skor dikurang satu biar pas sama index chart
                    moodMap[entryDate.dayOfMonth] = (entry.moodScore - 1).toFloat()
                }
                monthlyMoodData = moodMap
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
