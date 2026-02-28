package com.kelompok3.bloomu.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoodChart(moodData: List<Float>) {
    val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
    val emojis = listOf("😆", "😄", "🙂", "😢", "😵‍💫")

    val textMeasurer = rememberTextMeasurer()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Grafik Mood Kamu!",
            color = Color(0xFF2B2553),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .border(1.dp, Color(0xFFD6C5E3), RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                // space buat teks & emoji
                val yAxisSpace = 45.dp.toPx()
                val xAxisSpace = 30.dp.toPx()

                val chartWidth = width - yAxisSpace
                val chartHeight = height - xAxisSpace
                val stepX = chartWidth / (days.size - 1)
                val stepY = chartHeight / (emojis.size - 1)

                // bikin garis horizontal & naruh emoji
                for (i in emojis.indices) {
                    val y = i * stepY

                    drawLine(
                        color = Color(0xFF2B2553),
                        start = Offset(yAxisSpace, y),
                        end = Offset(width, y),
                        strokeWidth = 2f
                    )

                    val emojiResult = textMeasurer.measure(
                        text = emojis[i],
                        style = TextStyle(fontSize = 24.sp)
                    )
                    drawText(
                        textLayoutResult = emojiResult,
                        topLeft = Offset(0f, y - (emojiResult.size.height / 2))
                    )
                }

                // bikin label hari di bawah & garis kecilnya
                val labelY = chartHeight
                for (i in days.indices) {
                    val x = yAxisSpace + (i * stepX)

                    drawLine(
                        color = Color(0xFF2B2553),
                        start = Offset(x, chartHeight),
                        end = Offset(x, chartHeight + 8.dp.toPx()),
                        strokeWidth = 2f
                    )

                    val textResult = textMeasurer.measure(
                        text = days[i],
                        style = TextStyle(fontSize = 14.sp, color = Color.DarkGray)
                    )
                    drawText(
                        textLayoutResult = textResult,
                        topLeft = Offset(x - (textResult.size.width / 2), labelY + 16.dp.toPx())
                    )
                }

                // gambar garis grafiknya
                val path = Path()
                moodData.forEachIndexed { index, value ->
                    val x = yAxisSpace + (index * stepX)
                    val y = (4 - value) * stepY

                    if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }

                // kasih warna gradasi di garis
                drawPath(
                    path = path,
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFF7CA79), Color(0xFFE9543A)),
                        startY = 0f,
                        endY = chartHeight
                    ),
                    style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodChartPreview() {
    // 0 = 😵‍💫, 1 = 😢, 2 = 🙂, 3 = 😄, 4 = 😆
    val sampleMoodData = listOf(4f, 3f, 2f, 3.5f, 1f, 0.5f, 2.5f)
    MoodChart(moodData = sampleMoodData)
}