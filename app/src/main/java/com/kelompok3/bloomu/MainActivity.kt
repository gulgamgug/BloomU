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
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.jsonPrimitive

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var screen by remember {
                mutableStateOf("loading")
            }
            var emailUser by remember { mutableStateOf("") }
            var namaUser by remember { mutableStateOf("") }

            NavHost(
                navController = navController,
                startDestination = LoadingRoute
            ) {
                composable<LoadingRoute> {
                    LaunchedEffect(Unit) {
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
                    }
                    Text("Loading...")
                }

//                        // cek di hp udh login apa blom
//                        val status = supabase.auth.sessionStatus
//                            .filter { it is SessionStatus.Authenticated || it is SessionStatus.NotAuthenticated }
//                            .first()
//
//                        val user = supabase.auth.currentUserOrNull()
//                        if (user != null) {
//                            namaUser = user.userMetadata?.get("nama")?.jsonPrimitive?.content ?: "user"
//                            screen = "home"
//                        } else {
//                            screen = "register"
//                        }
//                    }

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