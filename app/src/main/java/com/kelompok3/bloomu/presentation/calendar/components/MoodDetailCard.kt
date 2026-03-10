package com.kelompok3.bloomu.presentation.calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.presentation.calendar.MoodEntry
import com.kelompok3.bloomu.presentation.dailycheckin.getMoodInfo
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MoodDetailCard(
    selectedDay: Int,
    moodEntry: MoodEntry?
) {
    if (moodEntry != null) {
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
                    val moodInfo = getMoodInfo(moodEntry.moodScore)

                    Image(
                        painter = painterResource(id = moodInfo.first),
                        contentDescription = "Mood Emoji",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Column {
                        Text(
                            text = moodInfo.second,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InterFontFamily
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = moodEntry.diaryNote ?: "Tidak ada catatan hari ini.",
                            color = Color.Black.copy(alpha = 0.7f),
                            fontSize = 11.sp,
                            lineHeight = 16.sp,
                            fontFamily = InterFontFamily
                        )
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(Color(0xFFF8F8F8), RoundedCornerShape(35.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tidak ada data mood untuk tanggal $selectedDay.",
                color = Color.Gray,
                fontFamily = InterFontFamily,
                fontSize = 14.sp
            )
        }
    }
}
