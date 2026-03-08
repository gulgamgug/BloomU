package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class AuthEvent {
    object LoginSuccess : AuthEvent()
    data class RegisterSuccess(val email: String) : AuthEvent()
    data class Error(val message: String) : AuthEvent()
}

class AuthViewModel : ViewModel() {
    // Shared states
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    
    // Register specific state
    var nama by mutableStateOf("")

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Actions
    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun onNamaChange(newValue: String) {
        nama = newValue
    }

    fun handleGoogleSignInResult(result: NativeSignInResult) {
        isLoading = false
        viewModelScope.launch {
            when (result) {
                is NativeSignInResult.Success -> {
                    _eventFlow.emit(AuthEvent.LoginSuccess)
                }
                is NativeSignInResult.Error -> {
                    _eventFlow.emit(AuthEvent.Error("Google Error: ${result.message}"))
                }
                else -> {}
            }
        }
    }

    fun login() {
        if (password.length < 8) {
            viewModelScope.launch {
                _eventFlow.emit(AuthEvent.Error("Password minimal 8 karakter"))
            }
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                AuthService.signIn(email, password)
                _eventFlow.emit(AuthEvent.LoginSuccess)
            } catch (e: Exception) {
                _eventFlow.emit(AuthEvent.Error("Email atau password salah"))
            } finally {
                isLoading = false
            }
        }
    }

    fun signUp() {
        if (password.length < 8) {
            viewModelScope.launch {
                _eventFlow.emit(AuthEvent.Error("Password minimal 8 karakter"))
            }
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                AuthService.signUp(email, password, nama)
                _eventFlow.emit(AuthEvent.RegisterSuccess(email))
            } catch (e: Exception) {
                _eventFlow.emit(AuthEvent.Error(e.message ?: "Terjadi kesalahan saat mendaftar"))
            } finally {
                isLoading = false
            }
        }
    }
}
