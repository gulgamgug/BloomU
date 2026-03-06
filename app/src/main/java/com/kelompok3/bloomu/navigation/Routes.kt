package com.kelompok3.bloomu.navigation

import kotlinx.serialization.Serializable

@Serializable object LoadingRoute
@Serializable object OnboardingRoute
@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object HomeRoute
@Serializable object AnalyticRoute
@Serializable object CareRoute
@Serializable object ProfileRoute
@Serializable object CheckInRoute

@Serializable data class OtpRoute(val email: String)
@Serializable data class DailyCheckInRoute(val moodData: List<Float>)

// CheckIn Steps
@Serializable object MoodSelectionStepRoute
@Serializable object QuestionsStepRoute
@Serializable object MiniDiaryStepRoute
@Serializable data class AssessmentResultRoute(
    val moodScore: Int,
    val mentalScore: Int,
    val physicalScore: Int,
    val academicScore: Int
)
