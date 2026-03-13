package com.kelompok3.bloomu.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun PerasaanCard(
    isCompleted: Boolean = false,
    onClick: () -> Unit
) {
    val gradientBrush =
        Brush.linearGradient(
        colors =
            if (!isCompleted) listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
            else listOf(Color(0xFFD3FFAF), Color(0xFF72AF5F)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) //supaya shadow keliatan
            .height(170.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(30.dp),
                ambientColor = Color.Black.copy(alpha = 0.5f),
                spotColor = Color.Black.copy(alpha = 0.5f)
            )
            .background(brush = gradientBrush, shape = RoundedCornerShape(30.dp))
            .clip(RoundedCornerShape(30.dp))
            .clickable(
                enabled = !isCompleted,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = true,
                    color = Color.Unspecified
                )
            ) { onClick() }
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally  ,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (isCompleted) "Kamu sudah mengisi untuk hari ini!" else "Masukkan perasaan hati kamu disini!",
                fontFamily = InterFontFamily,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(-25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                EmojiIcon(id = R.drawable.emoji5)
                EmojiIcon(id = R.drawable.emoji4)
                EmojiIcon(id = R.drawable.emoji3)
                EmojiIcon(id = R.drawable.emoji2)
                EmojiIcon(id = R.drawable.emoji_1)
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
        PerasaanCard(
            onClick = TODO()
        )
    }
}
