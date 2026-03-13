package com.kelompok3.bloomu.presentation.home

import androidx.lifecycle.ViewModel
import com.russhwolf.settings.Settings

class OnboardingViewModel : ViewModel() {
    private val settings = Settings()
//cek user pertama kali buka aplikasi  ataungga
    fun setOnboardingCompleted() {
        settings.putBoolean("onboarding_completed", true)
    }

    fun isOnboardingCompleted(): Boolean {
        return settings.getBoolean("onboarding_completed", false)
    }
}