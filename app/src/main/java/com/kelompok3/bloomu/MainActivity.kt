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
import com.kelompok3.bloomu.presentation.authentication.LoginScreen
import com.kelompok3.bloomu.presentation.authentication.OtpScreen
import com.kelompok3.bloomu.presentation.authentication.RegisterScreen
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.BloomUTheme
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
            var screen by remember {
                mutableStateOf("loading")
            }
            var emailUser by remember { mutableStateOf("")}
            var namaUser by remember { mutableStateOf("") }

            LaunchedEffect(Unit) {
                // cek di hp udh login apa blom
                val status = supabase.auth.sessionStatus
                    .filter { it is SessionStatus.Authenticated || it is SessionStatus.NotAuthenticated }
                    .first()

                val user = supabase.auth.currentUserOrNull()
                if (user != null) {
                    namaUser = user.userMetadata?.get("nama")?.jsonPrimitive?.content ?: "user"
                    screen = "home"
                } else {
                    screen = "register"
                }
            }

            when (screen) {
                "loading" -> Text("laoding")
                "login" -> LoginScreen(
                    onLoginSuccess = { _ -> screen = "home"},
                    onToRegisterScreen = { screen = "register" }
                )
                "register" -> RegisterScreen(
                    onRegisterSuccess = { email ->
                        emailUser = email // simpen email buat otp
                        screen = "otp" // pindah ke otp
                    },
                    onToLoginScreen = { screen = "login" }
                )
                "home" -> {
                    Text("Halo $namaUser! Kamu sudah login.")
                }

                "otp" -> {
                    OtpScreen(
                        email = emailUser,
                        onVerifSuccess = { screen = "home" }
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