package com.kelompok3.bloomu.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme

@Composable
fun PerasaanCard() {
    // Definisi Gradasi Linear F5C6EC dan 8366EB
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(16.dp)
            .background(brush = gradientBrush, shape = RoundedCornerShape(30.dp))
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally  ,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Masukkan perasaan hati kamu disini!",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Deret Emoji ke samping
            Row(
                horizontalArrangement = Arrangement.spacedBy(-25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EmojiIcon(id = R.drawable.emoji5)
                EmojiIcon(id = R.drawable.emoji4)
                EmojiIcon(id = R.drawable.emoji3)
                EmojiIcon(id = R.drawable.emoji2)
                EmojiIcon(id = R.drawable.emoji)
            }
        }
    }
}

@Composable
fun EmojiIcon(id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = "Emoji",
        modifier = Modifier.size(40.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PerasaanCardPreview() {
    BloomUTheme {
        PerasaanCard()
    }
}
