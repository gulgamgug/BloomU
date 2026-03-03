package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.FunFactCard
import com.kelompok3.bloomu.presentation.component.MoodChart
import com.kelompok3.bloomu.presentation.component.PerasaanCard
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Ellipse kanan atas
        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(700.dp)
                .offset(x = 100.dp, y = (-100).dp)
                .alpha(1.0f)
                .blur(35.dp)
        )

        // Ellipse kiri bawah
        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(700.dp)
                .offset(x = (-100).dp, y = 100.dp)
                .rotate(180f)
                .alpha(1.0f)
                .blur(35.dp)
        )

        Column(
            modifier =  Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // Bagian Atas
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Hi, $namaUser!",
                fontFamily = InterFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Apa yang sedang kamu rasakan saat ini?",
                fontFamily = InterFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        PerasaanCard()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Analitk",
                fontFamily = InterFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(Modifier.height(10.dp))
        
        // Bagian Tengah
        MoodChart(moodData = listOf(4f, 3f, 2f, 3.5f, 1f, 0.5f, 2.5f))
        
        Spacer(Modifier.height(-10.dp))
        
        // Bagian Bawah
        FunFactCard()
        
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
        }) { Text("Log Out", fontFamily = InterFontFamily) }
    }
}
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BloomUTheme {
        HomeScreen(onLogOutSuccess = {})
    }
}