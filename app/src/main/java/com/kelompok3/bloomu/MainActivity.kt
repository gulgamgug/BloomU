package com.kelompok3.bloomu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.kelompok3.bloomu.navigation.AppNavHost
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import io.github.jan.supabase.auth.handleDeeplinks

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        supabase.handleDeeplinks(intent)

        var keepSplashShown by mutableStateOf(true)
        splashScreen.setKeepOnScreenCondition { keepSplashShown }

        setContent {
            BloomUTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    onLoadingFinished = { keepSplashShown = false }
                )
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
