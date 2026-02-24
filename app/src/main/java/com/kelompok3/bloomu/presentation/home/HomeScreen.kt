package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable

fun HomeScreen(namaUser: String, onLogOutSuccess: () -> Unit) {
    val scope = rememberCoroutineScope()
    Column(
        modifier =  Modifier.fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen")
        Text("Halo $namaUser!")
        Spacer(Modifier.height(30.dp))
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