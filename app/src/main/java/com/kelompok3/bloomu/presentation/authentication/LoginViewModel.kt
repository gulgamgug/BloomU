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

sealed class LoginEvent {
    object Success : LoginEvent()
    data class Error(val message: String) : LoginEvent()
}

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun login() {
        if (password.length < 8) {
            viewModelScope.launch {
                _eventFlow.emit(LoginEvent.Error("Password minimal 8 karakter"))
            }
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                supabase.auth.signInWith(Email) {
                    this.email = this@LoginViewModel.email
                    this.password = this@LoginViewModel.password
                }
                _eventFlow.emit(LoginEvent.Success)
            } catch (e: Exception) {
                _eventFlow.emit(LoginEvent.Error("Email atau password salah"))
            } finally {
                isLoading = false
            }
        }
    }
}
