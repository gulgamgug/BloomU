package com.kelompok3.bloomu.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kelompok3.bloomu.presentation.authentication.LoginScreen
import com.kelompok3.bloomu.presentation.authentication.OtpScreen
import com.kelompok3.bloomu.presentation.authentication.RegisterScreen
import com.kelompok3.bloomu.presentation.home.HomeScreen
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus

@Composable
fun AppNavHost(
    navController: NavHostController,
    onLoadingFinished: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = LoadingRoute
    ) {
        composable<LoadingRoute> {
            LaunchedEffect(Unit) {
                // Cek status session saat ini di balik splash screen
                supabase.auth.sessionStatus.collect { status ->
                    if (status is SessionStatus.Authenticated || status is SessionStatus.NotAuthenticated) {
                        val user = supabase.auth.currentUserOrNull()
                        if (user != null) {
                            navController.navigate(HomeRoute) {
                                popUpTo(LoadingRoute) { inclusive = true }
                            }
                        } else {
                            navController.navigate(LoginRoute) {
                                popUpTo(LoadingRoute) { inclusive = true }
                            }
                        }
                        onLoadingFinished()
                    }
                }
            }
        }

        composable<LoginRoute> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },
                onToRegisterScreen = {
                    navController.navigate(RegisterRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<RegisterRoute> {
            RegisterScreen(
                onRegisterSuccess = { email ->
                    navController.navigate(OtpRoute(email))
                },
                onLoginSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo(RegisterRoute) { inclusive = true }
                    }
                },
                onToLoginScreen = {
                    navController.navigate(LoginRoute) {
                        popUpTo(RegisterRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<OtpRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<OtpRoute>()
            OtpScreen(
                email = args.email,
                onVerifSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo(OtpRoute(args.email)) { inclusive = true }
                        popUpTo(RegisterRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeRoute> {
            HomeScreen(
                onLogOutSuccess = {
                    navController.navigate(LoginRoute) {
                        popUpTo(HomeRoute) { inclusive = true }
                    }
                }
            )
        }
    }
}
