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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun Calenderpage() {
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
            Spacer(modifier = Modifier.height(24.dp))

            // Runtutan Api Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Runtutan Api",
                    color = Color(0xFF2A2567),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
                Spacer(modifier = Modifier.height(12.dp))
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 12.dp, shape = RoundedCornerShape(20.dp), clip = false)
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .border(1.dp, Color(0xFFF0F0F0).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.drawable.apistrek),
                            contentDescription = "Streak Icon",
                            modifier = Modifier.size(60.dp)
                        )
                        Text(
                            text = "4 Hari",
                            color = Color(0xFFFF9800),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InterFontFamily
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            (1..7).forEach { day ->
                                StreakItem(day, day <= 4)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Subtitle: Grafik mood kamu
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Grafik mood kamu",
                    color = Color(0xFF2A2567),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            // Perfect Semi-Circle Mood Chart
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(280.dp)
                        .height(140.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidthPx = 50.dp.toPx()
                        // To get a perfect semi-circle in a Box of W x H (where H = W/2):
                        // We draw a full circle of size W x W, but it's clipped by the Box height.
                        val arcSize = Size(size.width - strokeWidthPx, (size.width) - strokeWidthPx)
                        val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)

                        // Draw arcs from 180 degrees to 360 degrees (top half)
                        drawArc(
                            color = Color(0xFFFF4848),
                            startAngle = 180f,
                            sweepAngle = 36f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidthPx)
                        )
                        drawArc(
                            color = Color(0xFFFF9800),
                            startAngle = 216f,
                            sweepAngle = 36f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidthPx)
                        )
                        drawArc(
                            color = Color(0xFFFFD54F),
                            startAngle = 252f,
                            sweepAngle = 36f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidthPx)
                        )
                        drawArc(
                            color = Color(0xFF00E676),
                            startAngle = 288f,
                            sweepAngle = 36f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidthPx)
                        )
                        drawArc(
                            color = Color(0xFF7DB1F0),
                            startAngle = 324f,
                            sweepAngle = 36f,
                            useCenter = false,
                            topLeft = topLeft,
                            size = arcSize,
                            style = Stroke(width = strokeWidthPx)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(15.dp))
                
                Text(
                    text = "Total",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
                Text(
                    text = "7 Mood",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = InterFontFamily
                )
            }

            Spacer(modifier = Modifier.height(19.dp))

            // Legend Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(Color(0xFFFF4848), "Sangat Buruk")
                LegendItem(Color(0xFFFF9800), "Buruk")
                LegendItem(Color(0xFFFFD54F), "Biasa")
                LegendItem(Color(0xFF00E676), "Baik")
                LegendItem(Color(0xFF7DB1F0), "Sangat Baik")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Month Selector Button
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
                    .height(130.dp)
                    .background(Color.White, RoundedCornerShape(35.dp))
                    .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(35.dp))
                    .padding(horizontal = 20.dp, vertical = 12.dp)
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
                    Spacer(modifier = Modifier.height(16.dp))
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
                                text = "Seneng banget ketemu hari ini bisa ngerjain live coding lancar banget dapet nilai 100, ga sia-sia belajar full seminggu kemarin huhuuu",
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

@Composable
fun StreakItem(day: Int, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(42.dp)
                .height(28.dp), // Height matches 'auraapi' ratio
            contentAlignment = Alignment.Center
        ) {
            // The 'auraapi' is your custom rectangular gradient barrier
            Image(
                painter = painterResource(id = R.drawable.auraapi),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                // Grayscale effect for the barrier on inactive days
                colorFilter = if (!isActive) ColorFilter.colorMatrix(
                    ColorMatrix().apply { setToSaturation(0f) }
                ) else null,
                alpha = if (isActive) 1f else 0.4f
            )
            
            // The actual fire icon on top of the barrier
            Image(
                painter = painterResource(id = R.drawable.apistrek),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                // Grayscale effect for the fire icon on inactive days (newspaper style)
                colorFilter = if (!isActive) ColorFilter.colorMatrix(
                    ColorMatrix().apply { setToSaturation(0f) }
                ) else null,
                alpha = if (isActive) 1f else 0.7f
            )
        }
        
        Text(
            text = "Hari $day",
            fontSize = 10.sp,
            color = if (isActive) Color(0xFFFF9800) else Color.Gray,
            fontFamily = InterFontFamily,
            modifier = Modifier.padding(top = 4.dp),
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 6.dp)) {
        Box(modifier = Modifier.size(12.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = InterFontFamily
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun calenderpagePreview() {
    BloomUTheme(dynamicColor = false) {
        Calenderpage()
    }
}
