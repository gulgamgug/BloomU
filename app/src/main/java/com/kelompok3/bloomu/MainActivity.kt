package com.kelompok3.bloomu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kelompok3.bloomu.presentation.authentication.LoginScreen
import com.kelompok3.bloomu.presentation.authentication.OtpScreen
import com.kelompok3.bloomu.presentation.authentication.RegisterScreen
import com.kelompok3.bloomu.presentation.home.HomeScreen
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.navigation.*
import com.kelompok3.bloomu.presentation.component.LoadingDialog
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.handleDeeplinks
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.jsonPrimitive

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        supabase.handleDeeplinks(intent)
        var keepSplashShown = true
        splashScreen.setKeepOnScreenCondition { keepSplashShown }
        //enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = LoadingRoute
            ) {
                composable<LoadingRoute> {

                    LaunchedEffect(Unit) {
                        supabase.auth.sessionStatus.collect { status -> //cek status session saat ini dibalik splash screen
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
                                keepSplashShown = false
                            }
                        }

                    }

                }

                composable<LoginRoute> {
                    LoginScreen(
                        onLoginSuccess = { navController.navigate(HomeRoute) {
                            popUpTo(LoginRoute) { inclusive = true }
                        } },
                        onToRegisterScreen = { navController.navigate(RegisterRoute){
                            popUpTo(LoginRoute) { inclusive=true }
                        } }
                    )
                }


                composable<RegisterRoute> {
                    RegisterScreen(
                        onRegisterSuccess = { email ->
                            navController.navigate(OtpRoute(email))
                        },
                        onLoginSuccess = { navController.navigate(HomeRoute) {
                            popUpTo(RegisterRoute) { inclusive = true }
                        } },
                        onToLoginScreen = { navController.navigate(LoginRoute){
                            popUpTo(RegisterRoute) { inclusive = true }
                        } }
                    )
                }

                composable<OtpRoute> { backStackEntry ->
                    val args = backStackEntry.toRoute<OtpRoute>()
                    OtpScreen(
                        email = args.email,
                        onVerifSuccess = {
                            navController.navigate(HomeRoute){
                                popUpTo(OtpRoute(args.email)) { inclusive = true }
                            } }
                    )
                }

                composable<HomeRoute> {
                    val args = it.toRoute<HomeRoute>()
                    HomeScreen(
                        onLogOutSuccess = { navController.navigate(LoginRoute) {
                            popUpTo(HomeRoute) { inclusive = true }
                        } }
                    )
                }
            }
        }
    }
}






@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BloomUTheme {
        Greeting("Android")
    }
}