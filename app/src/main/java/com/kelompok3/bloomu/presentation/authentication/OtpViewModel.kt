package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class OtpEvent {
    object Success : OtpEvent()
    data class Error(val message: String) : OtpEvent()
    data class ResendSuccess(val message: String) : OtpEvent()
}

class OtpViewModel : ViewModel() {
    var timerSeconds by mutableStateOf(60)
    var otpCode by mutableStateOf("")
    var isError by mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<OtpEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (timerSeconds > 0) {
                delay(1000L)
                timerSeconds--
            }
        }
    }

    fun onOtpCodeChange(code: String) {
        otpCode = code
        if (isError) isError = false
    }

    fun verifyOtp(email: String) {
        viewModelScope.launch {
            try {
                supabase.auth.verifyEmailOtp(
                    type = OtpType.Email.EMAIL,
                    email = email,
                    token = otpCode
                )
                _eventFlow.emit(OtpEvent.Success)
            } catch (e: AuthRestException) {
                isError = true
                _eventFlow.emit(OtpEvent.Error("Kode OTP tidak valid"))
            } catch (e: HttpRequestException) {
                _eventFlow.emit(OtpEvent.Error("Kesalahan jaringan"))
            } catch (e: Exception) {
                _eventFlow.emit(OtpEvent.Error("Terjadi kesalahan verifikasi"))
            }
        }
    }

    fun resendOtp(email: String) {
        viewModelScope.launch {
            try {
                supabase.auth.resendEmail(
                    type = OtpType.Email.EMAIL,
                    email = email
                )
                timerSeconds = 60
                startTimer()
                _eventFlow.emit(OtpEvent.ResendSuccess("OTP berhasil dikirim ulang!"))
            } catch (e: AuthRestException) {
                _eventFlow.emit(OtpEvent.Error("Gagal: ${e.message}"))
            } catch (e: RestException) {
                _eventFlow.emit(OtpEvent.Error("Kesalahan server: ${e.message}"))
            } catch (e: HttpRequestException) {
                _eventFlow.emit(OtpEvent.Error("Kesalahan jaringan"))
            } catch (e: Exception) {
                _eventFlow.emit(OtpEvent.Error("Terjadi kesalahan: ${e.message}"))
            }
        }
    }
}
