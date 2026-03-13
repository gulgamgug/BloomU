package com.kelompok3.bloomu.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.notification.NotificationHelper
import com.kelompok3.bloomu.supabase.supabase
import com.russhwolf.settings.Settings
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive

sealed class ProfileEvent {
    object LogoutSuccess : ProfileEvent()
    data class Error(val message: String) : ProfileEvent()
}

class ProfileViewModel : ViewModel() {
    private val settings = Settings()

    var userName by mutableStateOf("Loading...")
        private set

    var userEmail by mutableStateOf("Loading...")
        private set

    var isNotificationEnabled by mutableStateOf(settings.getBoolean("daily_notif_enabled", false))
        private set

    private val _eventFlow = MutableSharedFlow<ProfileEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = supabase.auth.currentUserOrNull()
        if (user != null) {
            // ambil nama dari metadata (field "name" saat register)
            userName = user.userMetadata?.get("name")?.jsonPrimitive?.content ?: "User"
            userEmail = user.email ?: ""
        } else {
            userName = "Guest"
            userEmail = "-"
        }
    }

    fun toggleNotification(enabled: Boolean, context: android.content.Context) {
        isNotificationEnabled = enabled
        settings.putBoolean("daily_notif_enabled", enabled)

        if (enabled) {
            NotificationHelper.scheduleDailyNotification(context)
        } else {
            NotificationHelper.cancelDailyNotification(context)
        }
    }

    fun logout() {

        viewModelScope.launch {
            try {
                supabase.auth.signOut()
                _eventFlow.emit(ProfileEvent.LogoutSuccess)
            } catch (e: Exception) {
                _eventFlow.emit(ProfileEvent.Error("Gagal logout: ${e.message}"))
            }
        }
    }
}
