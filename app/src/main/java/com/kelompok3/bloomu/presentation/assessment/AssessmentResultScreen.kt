package com.kelompok3.bloomu.presentation.assessment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.dailycheckin.getMoodInfo
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun AssessmentResultScreen(
    moodScore: Int,
    mentalScore: Int,
    physicalScore: Int,
    academicScore: Int,
    onBackToHome: () -> Unit,
    viewModel: AssessmentResultViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.initialize(moodScore, mentalScore, physicalScore, academicScore)
    }

    val moodInfo = getMoodInfo(moodScore)
    val cardData = viewModel.assessmentCard

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background Ellipse dari Login Screen
        ShowEllipse(mode = 0)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Emoji di bagian atas (mengikuti MoodPage.kt logic melalui getMoodInfo)
            Image(
                painter = painterResource(id = moodInfo.first),
                contentDescription = "Mood Emoji",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Teks Status Suasana Hati
            Text(
                text = "Suasana hati kamu ${moodInfo.second.lowercase()}",
                color = Color(0xFF403959),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily,
                textAlign = TextAlign.Center
            )

            // Teks Tanggal (Format dari ViewModel)
            Text(
                text = viewModel.currentDate,
                color = Color(0xFF564553),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = InterFontFamily,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Matahari Senyum (CARD) - Image Dinamis
            cardData?.let {
                Image(
                    painter = painterResource(id = it.imageRes),
                    contentDescription = it.title,
                    modifier = Modifier.size(215.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(15.dp))

                // Teks Motivasi Tengah Bawah - Dinamis
                Text(
                    text = it.title,
                    color = Color(0xFF221E52),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = InterFontFamily
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = it.description,
                    color = Color(0xFF564553).copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    fontFamily = InterFontFamily,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Box Kebiasaan Kecil (Microactions Dinamis)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false
                        ),
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFF534A73).copy(alpha = 0.1f))
                ) {
                    Column {
                        // Header Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF534A73), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                                .padding(vertical = 12.dp, horizontal = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Kebiasaan kecil yang bisa kamu lakukan!",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = InterFontFamily,
                                textAlign = TextAlign.Center
                            )
                        }
                        // Isi Box (Microaction Terpilih)
                        Text(
                            text = it.microActions.firstOrNull() ?: "",
                            color = Color(0xFF403959),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = InterFontFamily,
                            modifier = Modifier.padding(16.dp),
                            lineHeight = 18.sp
                        )
                    }
                }
            } ?: run {
                // Loading state jika data belum siap
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Button Selesai
            Button(
                onClick = onBackToHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(25.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF221E52)),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Selesai",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Button Selesaikan misi baru (Gradasi) - Tetap dummy sementara
            val gradient = Brush.linearGradient(
                colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
            )

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(25.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gradient, shape = RoundedCornerShape(25.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Selesaikan misi baru",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssessmentResultPreview() {
    BloomUTheme(dynamicColor = false) {
        AssessmentResultScreen(
            moodScore = 5,
            mentalScore = 4,
            physicalScore = 4,
            academicScore = 4,
            onBackToHome = {}
        )
    }
}
