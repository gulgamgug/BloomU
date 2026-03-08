package com.kelompok3.bloomu.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.jsonPrimitive
import kotlin.time.ExperimentalTime

@Serializable
data class MoodEntry(
    @SerialName("mood_score") val moodScore: Int,
    @SerialName("created_at") val createdAt: String
)

sealed class HomeEvent {
    object LogoutSuccess : HomeEvent()
    data class Error(val message: String) : HomeEvent()
}

class HomeViewModel : ViewModel() {
    var namaUser by mutableStateOf("Loading..")
        private set

    var isCheckInCompletedToday by mutableStateOf(false)
        private set

    var weeklyMoodData by mutableStateOf(listOf(-1f, -1f, -1f, -1f, -1f, -1f, -1f))
        private set

    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadUserData()
        checkTodayCheckIn()
        fetchWeeklyMoodData()
    }

    private fun loadUserData() {
        val user = supabase.auth.currentUserOrNull()
        namaUser = user?.userMetadata?.get("name")?.jsonPrimitive?.content ?: "user"
    }

    @OptIn(ExperimentalTime::class)
    fun checkTodayCheckIn() {
        val user = supabase.auth.currentUserOrNull() ?: return

        viewModelScope.launch {
            try {
                val tz = TimeZone.currentSystemDefault()
                val now = kotlin.time.Clock.System.now().toLocalDateTime(tz)
                val localStart = LocalDateTime(now.year, now.month, now.dayOfMonth, 0, 0, 0, 0)
                val instantStart = localStart.toInstant(tz)
                val todayStartIso = instantStart.toString() 

                val response = supabase.postgrest["daily_checkins"].select(columns = Columns.list("id")) {
                    filter {
                        eq("user_id", user.id)
                        gte("created_at", todayStartIso)
                    }
                }

                isCheckInCompletedToday = response.data != "[]"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun fetchWeeklyMoodData() {
        val user = supabase.auth.currentUserOrNull() ?: return

        viewModelScope.launch {
            try {
                val tz = TimeZone.currentSystemDefault()
                val now = kotlin.time.Clock.System.now().toLocalDateTime(tz)
                
                // Cari hari Minggu terdekat
                // DayOfWeek.ordinal: MONDAY=0, ..., SUNDAY=6
                // Kita mau: Minggu=0, Senin=1, ..., Sabtu=6
                val currentDayValue = if (now.dayOfWeek == DayOfWeek.SUNDAY) 0 else now.dayOfWeek.ordinal + 1
                val sundayDate = now.date.minus(currentDayValue, DateTimeUnit.DAY)
                
                val sundayStart = LocalDateTime(sundayDate.year, sundayDate.month, sundayDate.dayOfMonth, 0, 0, 0, 0)
                val sundayStartIso = sundayStart.toInstant(tz).toString()

                val response = supabase.postgrest["daily_checkins"].select(columns = Columns.list("mood_score", "created_at")) {
                    filter {
                        eq("user_id", user.id)
                        gte("created_at", sundayStartIso)
                    }
                }

                val entries = response.decodeList<MoodEntry>()
                
                val moodList = MutableList(7) { -1f }
                entries.forEach { entry ->
                    val entryDate = Instant.parse(entry.createdAt).toLocalDateTime(tz)
                    val dayIndex = if (entryDate.dayOfWeek == DayOfWeek.SUNDAY) 0 else entryDate.dayOfWeek.ordinal + 1
                    
                    if (dayIndex in 0..6) {
                        moodList[dayIndex] = (entry.moodScore - 1).toFloat()
                    }
                }
                weeklyMoodData = moodList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                _eventFlow.emit(HomeEvent.LogoutSuccess)
            } catch (e: Exception) {
                _eventFlow.emit(HomeEvent.Error("Gagal logout"))
            }
        }
    }
}
