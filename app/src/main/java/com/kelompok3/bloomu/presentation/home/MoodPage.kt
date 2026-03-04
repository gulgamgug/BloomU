package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok3.bloomu.R

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
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-150).dp, y = (-150).dp)
                .size(500.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.4f), Color.Transparent)
                    )
                )
        )

        // Ellipse kanan bawah
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 150.dp, y = 150.dp)
                .size(500.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.4f), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Memberikan spacer agar box berada di tengah agak ke bawah
            Spacer(modifier = Modifier.weight(0.8f))

            // Box untuk Emoji
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.emoji5),
                        contentDescription = "Emoji 5",
                        modifier = Modifier.size(45.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.emoji4),
                        contentDescription = "Emoji 4",
                        modifier = Modifier.size(45.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.emoji3),
                        contentDescription = "Emoji 3",
                        modifier = Modifier.size(45.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.emoji2),
                        contentDescription = "Emoji 2",
                        modifier = Modifier.size(45.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.emoji),
                        contentDescription = "Emoji 1",
                        modifier = Modifier.size(45.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.8f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodPagePreview() {
    MoodPage()
}
