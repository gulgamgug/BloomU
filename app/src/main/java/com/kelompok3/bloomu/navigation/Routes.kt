package com.kelompok3.bloomu.navigation

import kotlinx.serialization.Serializable

@Serializable object LoadingRoute
@Serializable object LoginRoute
@Serializable object RegisterRoute
@Serializable object HomeRoute
@Serializable data class OtpRoute(val email: String)