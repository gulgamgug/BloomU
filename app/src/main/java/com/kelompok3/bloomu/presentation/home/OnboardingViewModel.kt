package com.kelompok3.bloomu.presentation.home

import androidx.lifecycle.ViewModel
import com.russhwolf.settings.Settings

class OnboardingViewModel : ViewModel() {
    private val settings = Settings()

    fun setOnboardingCompleted() {
        settings.putBoolean("onboarding_completed", true)
    }

    fun isOnboardingCompleted(): Boolean {
        return settings.getBoolean("onboarding_completed", false)
    }
}