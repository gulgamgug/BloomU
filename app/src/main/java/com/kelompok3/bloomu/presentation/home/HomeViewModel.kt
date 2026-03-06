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
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.jsonPrimitive
import kotlin.time.ExperimentalTime

sealed class HomeEvent {
    object LogoutSuccess : HomeEvent()
    data class Error(val message: String) : HomeEvent()
}

class HomeViewModel : ViewModel() {
    var namaUser by mutableStateOf("Loading..")
        private set

    var isCheckInCompletedToday by mutableStateOf(false)
        private set

    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadUserData()
        checkTodayCheckIn()
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
                // Mendapatkan tanggal hari ini dalam format YYYY-MM-DD
                val now = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val todayStart = "${now.year}-${now.monthNumber.toString().padStart(2, '0')}-${now.dayOfMonth.toString().padStart(2, '0')}T00:00:00Z"
                
                val response = supabase.postgrest["daily_checkins"].select(columns = Columns.list("id")) {
                    filter {
                        eq("user_id", user.id)
                        gte("created_at", todayStart)
                    }
                }
                
                isCheckInCompletedToday = response.data != "[]"
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
