package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

sealed class RegisterEvent {
    data class Success(val email: String) : RegisterEvent()
    data class Error(val message: String) : RegisterEvent()
}

class RegisterViewModel : ViewModel() {
    var nama by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _eventFlow = MutableSharedFlow<RegisterEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onNamaChange(newValue: String) {
        nama = newValue
    }

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun signUp() {
        if (password.length < 8) {
            viewModelScope.launch {
                _eventFlow.emit(RegisterEvent.Error("Password minimal 8 karakter"))
            }
            return
        }

        viewModelScope.launch {
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = this@RegisterViewModel.email
                    this.password = this@RegisterViewModel.password
                    data = buildJsonObject { put("name", nama) }
                }
                _eventFlow.emit(RegisterEvent.Success(email))
            } catch (e: Exception) {
                _eventFlow.emit(RegisterEvent.Error(e.message ?: "Terjadi kesalahan saat mendaftar"))
            }
        }
    }
}
