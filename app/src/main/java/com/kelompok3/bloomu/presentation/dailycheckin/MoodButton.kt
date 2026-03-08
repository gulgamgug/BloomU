package com.kelompok3.bloomu.presentation.dailycheckin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MoodButton(emoji: Int, moodId: Int, text: String, onClick: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = true,
                    color = Color.Unspecified
                )
            ) { onClick(moodId) },
    ) {
        Image(
            painter = painterResource(id = emoji),
            contentDescription = "Emoji 5",
            modifier = Modifier.size(45.dp)
        )
        Spacer(Modifier.height(7.dp))
        Text(
            text = text,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Color(0xFF2A2567),
            textAlign = TextAlign.Center
        )
    }
}