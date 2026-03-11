package com.kelompok3.bloomu.navigation

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.authentication.AuthService
import com.kelompok3.bloomu.presentation.authentication.ForgotPasswordScreen
import com.kelompok3.bloomu.presentation.authentication.LoginScreen
import com.kelompok3.bloomu.presentation.authentication.OtpScreen
import com.kelompok3.bloomu.presentation.authentication.RegisterScreen
import com.kelompok3.bloomu.presentation.authentication.sandiBaru
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.presentation.dailycheckin.CheckInScreen
import com.kelompok3.bloomu.presentation.home.OnboardingScreen
import com.kelompok3.bloomu.presentation.home.OnboardingViewModel
import com.kelompok3.bloomu.presentation.profile.editAkun
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take

@Composable
fun AppNavHost(
    navController: NavHostController,
    onLoadingFinished: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = LoadingRoute,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300)) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable<LoadingRoute> {
            val context = LocalContext.current
            val intent = (context as? Activity)?.intent
            val intentData = intent?.data?.toString() ?: ""
            // Cek apakah masuk lewat redirect reset password
            val isRecovery = intentData.startsWith("bloomu://") && intentData.contains("reset-password")

            android.util.Log.d("BloomU_Debug", "LoadingRoute: intentData=$intentData, isRecovery=$isRecovery")

            val onboardingViewModel: OnboardingViewModel = viewModel()
            var showLogography by remember { mutableStateOf(false) }
            var showProgressBar by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                onLoadingFinished()
                delay(500)
                showLogography = true
                delay(500)
                showProgressBar = true
            }

            LaunchedEffect(showProgressBar) {
                if (!showProgressBar) return@LaunchedEffect

                // Tunggu sampai status ditentukan oleh SDK (Authenticated atau Not)
                supabase.auth.sessionStatus
                    .filter { it is SessionStatus.Authenticated || it is SessionStatus.NotAuthenticated }
                    .take(1)
                    .collect { status ->
                        android.util.Log.d("BloomU_Debug", "Status Sesi: $status")
                        delay(500)
                        val user = supabase.auth.currentUserOrNull()
                        
                        if (user != null) {
                            if (isRecovery) {
                                navController.navigate(ResetPasswordRoute) {
                                    popUpTo(LoadingRoute) { inclusive = true }
                                }
                            } else {
                                navController.navigate(HomeRoute()) {
                                    popUpTo(LoadingRoute) { inclusive = true }
                                }
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
                    navController.navigate(HomeRoute()) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },
                onToRegisterScreen = {
                    navController.navigate(RegisterRoute) {
                        popUpTo(LoginRoute) { inclusive = true }
                    }
                },
                onToForgotPassword = {
                    navController.navigate(ForgotPasswordRoute)
                }
            )
        }

        composable<RegisterRoute> {
            RegisterScreen(
                onRegisterSuccess = { email ->
                    navController.navigate(OtpRoute(email))
                },
                onLoginSuccess = {
                    navController.navigate(HomeRoute()) {
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

        composable<ForgotPasswordRoute> {
            ForgotPasswordScreen(
                onBackToLogin = { navController.popBackStack() }
            )
        }

        composable<ResetPasswordRoute> {
            sandiBaru(
                onSuccess = {
                    navController.navigate(HomeRoute()) {
                        popUpTo(ResetPasswordRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<OtpRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<OtpRoute>()
            OtpScreen(
                email = args.email,
                onVerifSuccess = {
                    navController.navigate(HomeRoute()) {
                        popUpTo(OtpRoute(args.email)) { inclusive = true }
                        popUpTo(RegisterRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<HomeRoute>()
            com.kelompok3.bloomu.presentation.home.HomeNavBar(
                initialTab = route.selectedTab,
                onCheckInClick = { navController.navigate(CheckInRoute) },
                onLogOutSuccess = {
                    navController.navigate(LoginRoute) {
                        popUpTo(HomeRoute()) { inclusive = true }
                    }
                },
                onEditAccountClick = { navController.navigate(EditAccountRoute) }
            )
        }

        composable<EditAccountRoute> {
            editAkun(
                onBack = { navController.popBackStack() }
            )
        }

        composable<CheckInRoute> {
            CheckInScreen(
                onBack = { navController.popBackStack() },
                onFinished = { mood, mental, physical, academic ->
                    navController.navigate(
                        AssessmentResultRoute(mood, mental, physical, academic)
                    ) {
                        popUpTo(CheckInRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<AssessmentResultRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<AssessmentResultRoute>()
            com.kelompok3.bloomu.presentation.assessment.AssessmentResultScreen(
                moodScore = args.moodScore,
                mentalScore = args.mentalScore,
                physicalScore = args.physicalScore,
                academicScore = args.academicScore,
                onBackToHome = {
                    navController.navigate(HomeRoute()) {
                        popUpTo(HomeRoute()) { inclusive = true }
                    }
                }
            )
        }
    }
}
