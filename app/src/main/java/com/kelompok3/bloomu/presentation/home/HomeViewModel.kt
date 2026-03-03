package com.kelompok3.bloomu.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive

sealed class HomeEvent {
    object LogoutSuccess : HomeEvent()
    data class Error(val message: String) : HomeEvent()
}

class HomeViewModel : ViewModel() {
    var namaUser by mutableStateOf("Loading..")
        private set

    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val user = supabase.auth.currentUserOrNull()
        namaUser = user?.userMetadata?.get("name")?.jsonPrimitive?.content ?: "user"
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
