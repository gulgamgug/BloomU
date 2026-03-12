package com.kelompok3.bloomu.presentation.mission.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
fun polaTidur() {
    val lightPurpleBg = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )

    val sleepGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFFB8C00), Color(0xFF955300))
    )

    val darkBlue = Color(0xFF221E52)
    val sleepAccentColor = Color(0xFFFFA726) // Diubah ke oranye yang jauh lebih terang dan menyala

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightPurpleBg)
    ) {

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Box(modifier = Modifier.fillMaxWidth()) {

                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(33.dp)
                        .clickable { /* Back Logic */ }
                )

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

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(sleepGradient)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.istirahat),
                            contentDescription = "Pola Tidur Illustration",
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Pola Tidur",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InterFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Kategori Pola Tidur membantu tubuh dan pikiran mendapatkan waktu istirahat yang cukup. Kategori ini membantu mengurangi kelelahan dan membuat tubuh lebih rileks.",
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

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SleepActivityCard(
                        iconRes = R.drawable.hotel,
                        label = "Power nap 15 menit",
                        modifier = Modifier.weight(1f),
                        accentColor = sleepAccentColor
                    )
                    SleepActivityCard(
                        iconRes = R.drawable.mobile_hand_off,
                        label = "Matikan layar 30 menit sebelum tidur",
                        modifier = Modifier.weight(1f),
                        accentColor = sleepAccentColor
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SleepActivityCard(
                        iconRes = R.drawable.air,
                        label = "Tarik napas dalam 3 menit",
                        modifier = Modifier.weight(1f),
                        accentColor = sleepAccentColor
                    )
                    SleepActivityCard(
                        iconRes = R.drawable.physical_therapy__1_,
                        label = "Stretching sebelum tidur",
                        modifier = Modifier.weight(1f),
                        accentColor = sleepAccentColor
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
fun SleepActivityCard(iconRes: Int, label: String, modifier: Modifier, accentColor: Color) {
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
fun polaTidurPreview() {
    BloomUTheme(dynamicColor = false) {
        polaTidur()
    }
}
