package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun energi() {
    val lightPurpleBg = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )

    val energyGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFDD835), Color(0xFF978120))
    )

    val darkBlue = Color(0xFF221E52)
    val energyDarkGold = Color(0xFFD9B310) // Diubah ke warna yang jauh lebih terang

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightPurpleBg)
    ) {
        // --- BACKGROUND LAYER (Bloom Effect) ---
        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = "ellipse",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(1000.dp)
                .offset(y = (-450).dp)
                .rotate(0f)
                .alpha(0.7f)
                .blur(100.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = "ellipse_2",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(800.dp)
                .offset(y = (-350).dp)
                .rotate(90f)
                .alpha(0.5f)
                .blur(80.dp)
        )

        // --- Ellipse Tengah Kanan ---
        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = "ellipse_3",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(600.dp)
                .offset(x = 250.dp, y = 100.dp)
                .alpha(0.6f)
                .blur(80.dp)
        )

        // --- CONTENT LAYER ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Header Row
            Box(modifier = Modifier.fillMaxWidth()) {
                // Back Button (Original asset colors)
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(33.dp)
                        .clickable { /* Back Logic */ }
                )

                // Title
                Text(
                    text = "Misi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = InterFontFamily,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Main Banner Card
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(energyGradient)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.energi),
                            contentDescription = "Energi Illustration",
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Energi",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InterFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Description Paragraph
            Text(
                text = "Kategori Energi membantu meningkatkan energi tubuh dan membuat tubuh terasa lebih segar. Kategori ini membantu mengurangi rasa lelah dan meningkatkan semangat beraktivitas.",
                color = Color.Black,
                fontSize = 10.sp,
                lineHeight = 18.sp,
                fontFamily = InterFontFamily,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // "Aktivitas" Section Title
            Text(
                text = "Aktivitas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = InterFontFamily,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Activity Grid (2x2)
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ActivityCard(
                        iconRes = R.drawable.physical_therapy,
                        label = "Stretching 5 menit",
                        modifier = Modifier.weight(1f),
                        accentColor = energyDarkGold
                    )
                    ActivityCard(
                        iconRes = R.drawable.local_drink,
                        label = "Minum air cukup",
                        modifier = Modifier.weight(1f),
                        accentColor = energyDarkGold
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    ActivityCard(
                        iconRes = R.drawable.barefoot,
                        label = "Jalan kaki sebentar",
                        modifier = Modifier.weight(1f),
                        accentColor = energyDarkGold
                    )
                    ActivityCard(
                        iconRes = R.drawable.nature_people,
                        label = "Keluar ruangan 5 menit",
                        modifier = Modifier.weight(1f),
                        accentColor = energyDarkGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { /* Action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp), spotColor = darkBlue),
                colors = ButtonDefaults.buttonColors(containerColor = darkBlue),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Mulai Misi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = InterFontFamily
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ActivityCard(iconRes: Int, label: String, modifier: Modifier, accentColor: Color) {
    Box(
        modifier = modifier
            .height(100.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .border(1.5.dp, accentColor, RoundedCornerShape(20.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = accentColor,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                fontFamily = InterFontFamily,
                lineHeight = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun energiPreview() {
    BloomUTheme(dynamicColor = false) {
        energi()
    }
}
