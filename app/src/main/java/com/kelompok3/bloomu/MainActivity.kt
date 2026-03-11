package com.kelompok3.bloomu

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.kelompok3.bloomu.navigation.AppNavHost
import com.kelompok3.bloomu.navigation.CheckInRoute
import com.kelompok3.bloomu.navigation.DailyCheckInRoute
import com.kelompok3.bloomu.navigation.HomeRoute
import com.kelompok3.bloomu.navigation.LoadingRoute
import com.kelompok3.bloomu.navigation.LoginRoute
import com.kelompok3.bloomu.navigation.OnboardingRoute
import com.kelompok3.bloomu.navigation.OtpRoute
import com.kelompok3.bloomu.navigation.RegisterRoute
import com.kelompok3.bloomu.notification.NotificationHelper
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import io.github.jan.supabase.auth.handleDeeplinks

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            NotificationHelper.scheduleDailyNotification(this)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        android.util.Log.d("BloomU_Debug", "MainActivity onCreate intent: ${intent?.data}")
        supabase.handleDeeplinks(intent)

        checkNotificationPermission()

        var keepSplashShown by mutableStateOf(true)
        splashScreen.setKeepOnScreenCondition { keepSplashShown }

        setContent {
            BloomUTheme {
                val navController = rememberNavController()
                var showDebugMenu by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(
                        navController = navController,
                        onLoadingFinished = { keepSplashShown = false }
                    )

                    // Debug FAB (Tombol mengambang di pojok kanan bawah)
//                    FloatingActionButton(
//                        onClick = { showDebugMenu = true },
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(16.dp),
//                        containerColor = Color(0xFF221E52),
//                        contentColor = Color.White
//                    ) {
//                        Icon(Icons.Default.Build, contentDescription = "Debug Menu")
//                    }
//
//                    // Debug Bottom Sheet Menu
//                    if (showDebugMenu) {
//                        ModalBottomSheet(
//                            onDismissRequest = { showDebugMenu = false },
//                            sheetState = rememberModalBottomSheetState()
//                        ) {
//                            DebugMenuContent(
//                                onNavigate = { route ->
//                                    navController.navigate(route)
//                                    showDebugMenu = false
//                                }
//                            )
//                        }
//                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent) // Update intent utama
        android.util.Log.d("BloomU_Debug", "MainActivity onNewIntent intent: ${intent?.data}")
        supabase.handleDeeplinks(intent)
    }

    private fun checkNotificationPermission() {
        val settings = com.russhwolf.settings.Settings()
        val isEnabled = settings.getBoolean("daily_notif_enabled", false)
        
        if (!isEnabled) return // Jangan cek izin/jadwalin kalo emang dimatiin

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationHelper.scheduleDailyNotification(this)
            }
            // Kalo belum granted, biarin user nyalain manual di profil
        } else {
            NotificationHelper.scheduleDailyNotification(this)
        }
    }
}

@Composable
fun DebugMenuContent(onNavigate: (Any) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Debug Navigation",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF221E52)
        )

        // Daftar Route yang bisa dikunjungi
        val routes = listOf(
            "Loading Screen" to LoadingRoute,
            "Onboarding" to OnboardingRoute,
            "Login Screen" to LoginRoute,
            "Register Screen" to RegisterRoute,
            "Home Screen" to HomeRoute,
            "Check-In (Daily)" to CheckInRoute,
            "OTP Screen (test@mail.com)" to OtpRoute("test@mail.com"),
            "Daily Result (test)" to DailyCheckInRoute(listOf(4f, 3f, 5f))
        )

        routes.forEach { (label, route) ->
            Button(
                onClick = { onNavigate(route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9383CC))
            ) {
                Text(label)
            }
        }
        Spacer(Modifier.height(32.dp))
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
