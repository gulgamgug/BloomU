package com.kelompok3.bloomu.presentation.calendar

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun CalendarPage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Background Ellipse (Soft gradient background)
        ShowEllipse(mode = 0)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 40.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Kalender Mood
            Text(
                text = "Kalender Mood",
                color = Color(0xFF2A2567),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily,
                modifier = Modifier.padding(top = 24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle: Grafik mood kamu
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Grafik mood kamu",
                    color = Color(0xFF2A2567),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Donut Chart Area
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 55.dp.toPx()

                    // Segment Sangat Baik (Biru) - Kanan Bawah
                    drawArc(
                        color = Color(0xFF7DB1F0),
                        startAngle = 40f,
                        sweepAngle = 100f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                    // Segment Baik (Hijau) - Kanan Atas
                    drawArc(
                        color = Color(0xFF00E676),
                        startAngle = 270f,
                        sweepAngle = 130f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                    // Segment Buruk (Oranye) - Kiri Atas
                    drawArc(
                        color = Color(0xFFFF9800),
                        startAngle = 185f,
                        sweepAngle = 85f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                    // Segment Biasa (Kuning) - Kiri Tengah
                    drawArc(
                        color = Color(0xFFFFD54F),
                        startAngle = 175f,
                        sweepAngle = 10f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                    // Segment Sangat Buruk (Merah) - Kiri Bawah
                    drawArc(
                        color = Color(0xFFFF4848),
                        startAngle = 140f,
                        sweepAngle = 35f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                }

                // Lubang Tengah Donut (Putih)
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.White, CircleShape)
                )

//                // Emoji di atas Chart (Posisi disesuaikan agar pas di tengah warna)
//                EmojiOnChart(R.drawable.emoji2, Alignment.CenterEnd, xOffset = (-25).dp, yOffset = 70.dp) // Sangat Baik
//                EmojiOnChart(R.drawable.emoji, Alignment.TopEnd, xOffset = (-40).dp, yOffset = 50.dp) // Baik (placeholder icon)
//                // Using different emoji for better representation matching the image
//                EmojiOnChart(R.drawable.emoji, Alignment.TopCenter, yOffset = 10.dp) // Baik
//                EmojiOnChart(R.drawable.emoji3, Alignment.CenterStart, xOffset = 5.dp, yOffset = (-35).dp) // Biasa
//                EmojiOnChart(R.drawable.emoji4, Alignment.CenterStart, xOffset = 0.dp, yOffset = (0).dp) // Buruk
//                EmojiOnChart(R.drawable.emoji5, Alignment.BottomStart, xOffset = 45.dp, yOffset = (-25).dp) // Sangat Buruk
//                EmojiOnChart(R.drawable.emoji, Alignment.BottomEnd, xOffset = (-25).dp, yOffset = (-45).dp) // Sangat Baik
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Legend Row (Titik-titik warna)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                LegendItem(Color(0xFFFF4848), "Sangat Buruk")
//                LegendItem(Color(0xFFFF9800), "Buruk")
//                LegendItem(Color(0xFFFFD54F), "Biasa")
//                LegendItem(Color(0xFF00E676), "Baik")
//                LegendItem(Color(0xFF7DB1F0), "Sangat Baik")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Month Selector Button (Pill Shape)
            Box(
                modifier = Modifier
                    .background(Color(0xFF2A2567), RoundedCornerShape(25.dp))
                    .padding(horizontal = 24.dp, vertical = 10.dp)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Februari",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = InterFontFamily
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        painter = painterResource(id = android.R.drawable.arrow_down_float),
                        contentDescription = "Select Month",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Calendar Card Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(120.dp)
                    .background(Color.White, RoundedCornerShape(35.dp))
                    .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(35.dp))
                    .padding(horizontal = 20.dp, vertical = 9.dp)
            ) {
                Column {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        listOf("S", "R", "K", "J", "S", "M", "S").forEach { day ->
                            Text(
                                text = day,
                                color = Color(0xFF2A2567),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.width(36.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Date 1-5 (Circle placeholders)
                        (1..5).forEach { day ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(36.dp)) {
                                Box(modifier = Modifier.size(24.dp).background(Color(0xFFE9E3FF), CircleShape))
                                Text(
                                    text = "$day",
                                    fontSize = 12.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                        // Date 6 with Emoji
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(34.dp)) {
                            Image(painter = painterResource(id = R.drawable.emoji), contentDescription = null, modifier = Modifier.size(30.dp))
                            Text(text = "6", fontSize = 12.sp, color = Color.Black, modifier = Modifier.padding(top = 8.dp))
                        }
                        // Date 7 with Emoji
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(34.dp)) {
                            Image(painter = painterResource(id = R.drawable.emoji4), contentDescription = null, modifier = Modifier.size(30.dp))
                            Text(text = "7", fontSize = 12.sp, color = Color.Black, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mood Detail Card Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(Color.White, RoundedCornerShape(35.dp))
                    .border(1.dp, Color(0xFFF5C6EC), RoundedCornerShape(35.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Mood",
                        color = Color(0xFF2A2567),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = InterFontFamily
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.emoji),
                            contentDescription = "Mood Emoji",
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(
                                text = "Sangat Baik",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = InterFontFamily
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Seneng banget ketemu hari ini bisa ngerjain live coding lancar banget dapet nilai 100, ga sia-sia belajar full seminggu kemarin bubuuu",
                                color = Color.Black.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                lineHeight = 16.sp,
                                fontFamily = InterFontFamily
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun EmojiOnChart(emojiId: Int, alignment: Alignment, xOffset: androidx.compose.ui.unit.Dp = 0.dp, yOffset: androidx.compose.ui.unit.Dp = 0.dp) {
//    Box(modifier = Modifier.fillMaxSize()) {
//        Image(
//            painter = painterResource(id = emojiId),
//            contentDescription = null,
//            modifier = Modifier
//                .size(32.dp)
//                .align(alignment)
//                .offset(x = xOffset, y = yOffset)
//        )
//    }
//}

//@Composable
//fun LegendItem(color: Color, label: String) {
//    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 6.dp)) {
//        Box(modifier = Modifier.size(12.dp).background(color, CircleShape))
//        Spacer(modifier = Modifier.width(6.dp))
//        Text(
//            text = label,
//            fontSize = 10.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            fontFamily = InterFontFamily
//        )
//    }
//}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun calendarPagePreview() {
//    BloomUTheme(dynamicColor = false) {
//        CalendarPage()
//    }
//}
