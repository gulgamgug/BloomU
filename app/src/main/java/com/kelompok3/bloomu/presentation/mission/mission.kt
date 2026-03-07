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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Judul "Misi" di tengah atas
            Text(
                text = "Misi",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Api Strek
            Image(
                painter = painterResource(id = R.drawable.apistrek),
                contentDescription = "Api Strek",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Teks "4 Hari" dengan Radial Gradient
            val radialGradientBrush = Brush.radialGradient(
                colors = listOf(Color(0xFFFF9800), Color(0xFFFF6D00), Color(0xFFF44336)),
            )

            Text(
                text = "4 Hari",
                style = TextStyle(
                    brush = radialGradientBrush,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Row Api Strek (7 Hari)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (i in 1..7) {
                    val isColored = i <= 4
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Efek Box Glow Radial
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFFFDCDC))
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.apistrek),
                                contentDescription = "Api Hari $i",
                                modifier = Modifier.size(24.dp),
                                colorFilter = if (isColored) null else ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Hari $i",
                            color = if (isColored) Color(0xFF403959) else Color.Gray,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = InterFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Subjudul "Kategori" di kiri
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

            // Button Kategori (Rounded 2 berjejer)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Button Kiri: Kebiasaan kecil
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9383CC)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Kebiasaan kecil",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                }

                // Button Kanan: Energi
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9383CC)),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Energi",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = InterFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Subjudul "Misi Harian Kamu!"
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

            // Mission Item 1: Rapikan tempat tidurmu
            MissionItem(
                title = "Rapikan tempat tidurmu",
                description = "Langkah kecil yang bikin pikiran lebih teratur, mood lebih positif, dan siap menghadapi aktivitas seharian.",
                estimation = "Estimasi waktu: 3-5 menit",
                isFinished = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mission Item 2: Lari pagi
            MissionItem(
                title = "Lari pagi",
                description = "Gerakan ringan yang bantu melepaskan stres, meningkatkan energi, dan bikin mood lebih fresh!.",
                estimation = "Estimasi waktu: 30-60 menit",
                isFinished = false
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mission Item 3: Selesai (8 gelas air putih)
            MissionItem(
                title = "8 gelas air putih setiap hari",
                description = "Hidrasi yang cukup bantu tubuh lebih segar dan pikiran lebih fokus!",
                estimation = "Estimasi waktu: 3-5 menit",
                isFinished = true
            )

            Spacer(modifier = Modifier.height(40.dp))
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
            // Checklist Icon
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isFinished) Color.White.copy(alpha = 0.3f) else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = if (isFinished) Color.White else Color.Gray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isFinished) {
                    Text(
                        text = "✓",
                        color = Color.White,
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
