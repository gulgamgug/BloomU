package com.kelompok3.bloomu.presentation.dailycheckin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.presentation.dailycheckin.components.MoodButton
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MoodPage(
    onBack: () -> Unit,
    onMoodSelected: (Int) -> Unit
) {
    val gradientBackground = Brush.linearGradient(
        colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
    )
    ShowEllipse(2)


        IconButton(
            onClick = onBack,
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .size(40.dp)
                .background(
                    Color(0xFF2A2567),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bagaimana perasaan kamu hari ini?",
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color(0xFF2A2567),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            val currentDate = remember {
                SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID")).format(Date())
            }

            Text(
                text = currentDate,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Color(0xFF000000).copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 24.dp),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .background(
                        color = Color.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(horizontal = 30.dp, vertical = 30.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                   MoodButton(
                       emoji = R.drawable.emoji5,
                       moodId = 1,
                       text = "Sangat\nBuruk",
                       onClick = onMoodSelected
                   )
                    MoodButton(
                        emoji = R.drawable.emoji4,
                        moodId = 2,
                        text = "Buruk",
                        onClick = onMoodSelected
                    )
                    MoodButton(
                        emoji = R.drawable.emoji3,
                        moodId = 3,
                        text = "Biasa",
                        onClick = onMoodSelected
                    )
                    MoodButton(
                        emoji = R.drawable.emoji2,
                        moodId = 4,
                        text = "Baik",
                        onClick = onMoodSelected
                    )
                    MoodButton(
                        emoji = R.drawable.emoji_1,
                        moodId = 5,
                        text = "Sangat\nBaik",
                        onClick = onMoodSelected
                    )
                }
            }

            //Spacer(modifier = Modifier.weight(0.8f))
        }
    }


@Preview(showBackground = true)
@Composable
fun MoodPagePreview() {
    MoodPage(
        onMoodSelected = {},
        onBack = {}
    )
}
