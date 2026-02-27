package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.serialization.json.jsonPrimitive

@Composable

fun HomeScreen(onLogOutSuccess: () -> Unit) {
    var namaUser by remember { mutableStateOf("Loading..") }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        val user = supabase.auth.currentUserOrNull()
        namaUser = user?.userMetadata?.get("name")?.jsonPrimitive?.content ?: "user"
    }

    Column(
        modifier =  Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen")
        Text("Halo $namaUser!")
        Spacer(Modifier.height(30.dp))
        Image(painter = painterResource(R.drawable.emoji), contentDescription = "Logo",
        modifier = Modifier.size(100.dp))
        Button(onClick = {
            scope.launch {
                try {
                    supabase.auth.signOut()
                    onLogOutSuccess()
                } catch (e: Exception) {
                    println("error logout")
                }
            }
        }) { Text("Log Out") }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BloomUTheme {
        HomeScreen(onLogOutSuccess = {})
    }
}