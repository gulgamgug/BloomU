package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun Mission() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- BACKGROUND LAYER ---
        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(400.dp)
                .offset(x = (-120).dp, y = (-120).dp)
                .rotate(45f)
                .alpha(1.0f)
                .blur(45.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(500.dp)
                .offset(x = 150.dp, y = 100.dp)
                .alpha(0.7f)
                .blur(50.dp)
        )

        // --- CONTENT LAYER ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            // Header Area
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Misi",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mission Header Card
            MissionHeaderCard()

            Spacer(modifier = Modifier.height(32.dp))

            // Kategori Section
            Text(
                text = "Kategori",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, Color(0xFF9383CC)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Kebiasaan kecil",
                        color = Color(0xFF9383CC),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                }

                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(1.dp, Color(0xFF9383CC)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Energi",
                        color = Color(0xFF9383CC),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Misi Harian Kamu!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )

            Text(
                text = "Misi kecil untuk menjaga ritmemu",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(16.dp))

            MissionItem(
                title = "Rapikan tempat tidurmu",
                description = "Langkah kecil yang bikin pikiran lebih teratur, mood lebih positif, dan siap menghadapi aktivitas seharian.",
                estimation = "Estimasi waktu: 3-5 menit",
                isFinished = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            MissionItem(
                title = "Lari pagi",
                description = "Gerakan ringan yang bantu melepaskan stres, meningkatkan energi, dan bikin mood lebih fresh!.",
                estimation = "Estimasi waktu: 30-60 menit",
                isFinished = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            MissionItem(
                title = "8 gelas air putih setiap hari",
                description = "Hidrasi yang cukup bantu tubuh lebih segar dan pikiran lebih fokus!",
                estimation = "Estimasi waktu: 3-5 menit",
                isFinished = true
            )

            Spacer(modifier = Modifier.height(40.dp))
        }

        // Icon Back diletakkan di luar Column agar tetap di pojok
        IconButton(
            onClick = { /* Navigasi kembali */ },
            modifier = Modifier
                .padding(top = 60.dp, start = 16.dp)
                .align(Alignment.TopStart)
                .size(33.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = Color.Unspecified,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun MissionHeaderCard() {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(185.dp)
            .background(brush = gradientBrush, shape = RoundedCornerShape(24.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.olahraguy),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(150.dp)
                .padding(end = 8.dp, bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Selesaikan misi harianmu!",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Jaga pola hidup yang sehat dan capai hidup yang lebih produktif!",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = InterFontFamily,
                modifier = Modifier.width(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.wrapContentSize()
            ) {
                Text(
                    text = "1/3 misi telah selesai!",
                    color = Color(0xFFB8A4FF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MissionItem(
    title: String,
    description: String,
    estimation: String,
    isFinished: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isFinished) 0.dp else 4.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        color = if (isFinished) Color(0xFF8A7BBF) else Color.White,
        border = if (isFinished) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Checklist Icon dengan Efek Hijau
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isFinished) Color(0xFFE8F5E9) else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = if (isFinished) Color(0xFF4CAF50) else Color.Gray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isFinished) {
                    Text(
                        text = "✓",
                        color = Color(0xFF9383CC), // Warna ungu button sesuai permintaan
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = if (isFinished) Color.White else Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    color = if (isFinished) Color.White.copy(alpha = 0.9f) else Color.Black.copy(alpha = 0.7f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = InterFontFamily,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = estimation,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = if (isFinished) Color.White else Color(0xFF9383CC),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = InterFontFamily
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MissionPreview() {
    BloomUTheme(dynamicColor = false) {
        Mission()
    }
}
