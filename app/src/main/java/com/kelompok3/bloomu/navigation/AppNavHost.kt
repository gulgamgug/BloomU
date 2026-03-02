package com.kelompok3.bloomu.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.authentication.LoginScreen
import com.kelompok3.bloomu.presentation.authentication.OtpScreen
import com.kelompok3.bloomu.presentation.authentication.RegisterScreen
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.presentation.home.HomeScreen
import com.kelompok3.bloomu.presentation.home.OnboardingScreen
import com.kelompok3.bloomu.presentation.home.OnboardingViewModel
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.delay

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
            val onboardingViewModel: OnboardingViewModel = viewModel() // Inisialisasi ViewModel di sini
            var showLogography by remember { mutableStateOf(false) }
            var showProgressBar by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                onLoadingFinished() // Matikan native splash screen
                delay(500)          // Tunggu setengah detik
                showLogography = true // Munculkan tulisan bloomu
                delay(500)
                showProgressBar = true // Munculkan loading bar
            }

            LaunchedEffect(showProgressBar) {
                if (showProgressBar) {
                    supabase.auth.sessionStatus.collect { status ->
                        if (status is SessionStatus.Authenticated || status is SessionStatus.NotAuthenticated) {
                            delay(250) // Supaya loaidng nya keliatan
                            val user = supabase.auth.currentUserOrNull()
                            
                            if (user != null) {
                                navController.navigate(HomeRoute) {
                                    popUpTo(LoadingRoute) { inclusive = true }
                                }
                            } else {
                                if (onboardingViewModel.isOnboardingCompleted()) {
                                    navController.navigate(LoginRoute) {
                                        popUpTo(LoadingRoute) { inclusive = true }
                                    }
                                } else {
                                    navController.navigate(OnboardingRoute) {
                                        popUpTo(LoadingRoute) { inclusive = true }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            ShowEllipse(0) //background

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_gambar),
                        contentDescription = "Logo Gambar",
                        modifier = Modifier.size(165.dp, 189.dp)
                    )

                    AnimatedVisibility(
                        visible = showLogography,
                        enter = fadeIn() + expandVertically()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(Modifier.height(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.logography),
                                contentDescription = "Logo Text",
                                modifier = Modifier.size(278.dp, 54.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(40.dp))
                    Box(modifier = Modifier.height(40.dp)) {
                        if (showProgressBar) {
                            CircularProgressIndicator(
                                color = Color(0xFF221E52),
                                strokeWidth = 3.dp
                            )
                        }
                    }
                }
            }
        }

        composable<OnboardingRoute> {
            val onboardingViewModel: OnboardingViewModel = viewModel() // Perbaiki cara panggil VM
            OnboardingScreen(
                onFinished = {
                    onboardingViewModel.setOnboardingCompleted()
                    navController.navigate(LoginRoute) {
                        popUpTo(OnboardingRoute) { inclusive = true }
                    }
                }
            )
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
