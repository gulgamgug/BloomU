package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.AuthService
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class AuthEvent {
    object LoginSuccess : AuthEvent()
    data class RegisterSuccess(val email: String) : AuthEvent()
    object ResetPasswordEmailSent : AuthEvent()
    object PasswordUpdated : AuthEvent()
    data class Error(val message: String) : AuthEvent()
}

class AuthViewModel : ViewModel() {
    // Shared state
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    
    // Register state spesifik
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
        // Validasi input kosong
        if (nama.isBlank() || email.isBlank() || password.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(AuthEvent.Error("Semua kolom harus diisi"))
            }
            return
        }

        // Validasi format email
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        if (!email.matches(emailPattern)) {
            viewModelScope.launch {
                _eventFlow.emit(AuthEvent.Error("Format email tidak valid"))
            }
            return
        }

        // Validasi panjang password
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
                val errorMessage = e.message ?: ""
                val userFriendlyMessage = when {
                    errorMessage.contains("User already registered", ignoreCase = true) || 
                    errorMessage.contains("already exists", ignoreCase = true) -> {
                        "Email sudah terdaftar. Silahkan menggunakan layanan login."
                    }
                    errorMessage.contains("network", ignoreCase = true) -> {
                        "Koneksi internet bermasalah"
                    }
                    else -> "Terjadi kesalahan saat mendaftar: $errorMessage"
                }
                _eventFlow.emit(AuthEvent.Error(userFriendlyMessage))
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPassword() {
        if (email.isBlank()) {
            viewModelScope.launch {
                _eventFlow.emit(AuthEvent.Error("Email harus diisi"))
            }
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                AuthService.sendResetPasswordEmail(email)
                _eventFlow.emit(AuthEvent.ResetPasswordEmailSent)
            } catch (e: Exception) {
                _eventFlow.emit(AuthEvent.Error("Gagal mengirim email reset: ${e.message}"))
            } finally {
                isLoading = false
            }
        }
    }

    fun updatePassword() {
        if (password.length < 8) {
            viewModelScope.launch {
                _eventFlow.emit(AuthEvent.Error("Password minimal 8 karakter"))
            }
            return
        }

        isLoading = true
        viewModelScope.launch {
            try {
                AuthService.updatePassword(password)
                _eventFlow.emit(AuthEvent.PasswordUpdated)
            } catch (e: Exception) {
                _eventFlow.emit(AuthEvent.Error("Gagal memperbarui password: ${e.message}"))
            } finally {
                isLoading = false
            }
        }
    }
}
