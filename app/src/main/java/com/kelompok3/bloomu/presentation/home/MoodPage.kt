package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
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
fun MoodPage() {
    // Background utama Linear Gradient
    val gradientBackground = Brush.linearGradient(
        colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        // Ellipse kiri atas
        Canvas(
            modifier = Modifier
                .offset(x = (-100).dp, y = (-100).dp)
                .size(600.dp) // Ukuran Canvas lebih besar dari radius
                .blur(100.dp) // Blur lebih besar agar lebih lembut
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.8f),
                        Color.White.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = Offset.Zero,
                    radius = 450.dp.toPx() // Radius lebih kecil dari ukuran Canvas agar tidak terpotong
                )
            )
        }

        // Ellipse kanan bawah
        Canvas(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 100.dp, y = 100.dp)
                .size(600.dp)
                .blur(100.dp)
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.8f),
                        Color.White.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = Offset(size.width, size.height),
                    radius = 450.dp.toPx()
                )
            )
        }

        // Button Back (Kiri Atas)
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 24.dp)
                .size(34.dp)
                .clickable { /* Aksi navigasi balik */ }
        )


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.8f))

            // Teks Pertanyaan
            Text(
                text = "Bagaimana perasaan\nkamu hari ini?",
                color = Color(0xFF2A2567),
                fontFamily = InterFontFamily,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )

            // Teks Tanggal
            Text(
                text = "4 Maret 2026",
                color = Color(0xFF564553),
                fontFamily = InterFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Box untuk Emoji (Putih Solid dengan Height)
            Box(
                modifier = Modifier
                    .width(345.dp)
                    .height(125.dp) // Memberikan height spesifik
                    .background(
                        color = Color.White, // Putih solid
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Membuat daftar pasangan antara ID Gambar dan Label Teks
                    val moodData = listOf(
                        R.drawable.emoji5 to "Sangat Buruk",
                        R.drawable.emoji4 to "Buruk",
                        R.drawable.emoji3 to "Biasa",
                        R.drawable.emoji2 to "Baik",
                        R.drawable.emoji to "Sangat Baik"
                    )
                    
                    moodData.forEach { (emojiId, label) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = emojiId),
                                contentDescription = label,
                                modifier = Modifier
                                    .size(43.dp)
                                    .clickable { /* Aksi ketika emoji diklik */ }
                            )
                            Text(
                                text = label,
                                color = Color(0xFF201C4D),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = InterFontFamily
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MoodPagePreview() {
    BloomUTheme(dynamicColor = false) {
        MoodPage()
    }
}
