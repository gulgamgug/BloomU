package com.kelompok3.bloomu.presentation.calendar.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MoodChartSection(
    distribution: List<Int>,
    totalMoods: Int
) {
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
                val arcSize = Size(size.width - strokeWidthPx, (size.width) - strokeWidthPx)
                val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)

                if (totalMoods == 0) {
                    drawArc(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidthPx)
                    )
                } else {
                    var currentStartAngle = 180f
                    val colors = listOf(
                        Color(0xFFFF4848), // Sangat Buruk
                        Color(0xFFFF9800), // Buruk
                        Color(0xFFFFD54F), // Biasa
                        Color(0xFF00E676), // Baik
                        Color(0xFF7DB1F0)  // Sangat Baik
                    )

                    distribution.forEachIndexed { index, count ->
                        val sweepAngle = (count.toFloat() / totalMoods) * 180f
                        if (sweepAngle > 0) {
                            drawArc(
                                color = colors[index],
                                startAngle = currentStartAngle,
                                sweepAngle = sweepAngle,
                                useCenter = false,
                                topLeft = topLeft,
                                size = arcSize,
                                style = Stroke(width = strokeWidthPx)
                            )
                            currentStartAngle += sweepAngle
                        }
                    }
                }
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
            text = "$totalMoods Mood",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = InterFontFamily
        )
        
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
