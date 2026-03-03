package com.kelompok3.bloomu.navigation

import kotlinx.serialization.Serializable

@Serializable object LoadingRoute
@Serializable object OnboardingRoute
@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object HomeRoute
@Serializable object CheckInRoute
@Serializable data class OtpRoute(val email: String)
@Serializable data class DailyCheckInRoute(val moodData: List<Float>)
