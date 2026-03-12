package com.kelompok3.bloomu.presentation.calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.calendar.MoodEntry
import com.kelompok3.bloomu.presentation.dailycheckin.getMoodInfo
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MoodDetailCard(
    selectedDay: Int,
    moodEntry: MoodEntry?,
    onCheckInClick: () -> Unit = {}
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
                            //mood title
                            text = moodInfo.second,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = InterFontFamily
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        // detail per domain
                        Column(
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy((-6).dp)
                        ) {
                            DomainScoreText("Pikiran", getMentalStatus(moodEntry.mentalScore ?: 0))
                            DomainScoreText("Tubuh", getPhysicalStatus(moodEntry.physicalScore ?: 0))
                            DomainScoreText("Fokus", getAcademicStatus(moodEntry.academicScore ?: 0))
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            //diary
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
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sentiment_dissatisfied_24),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Tidak ada data pada tanggal $selectedDay",
                    color = Color.Gray,
                    fontFamily = InterFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun DomainScoreText(label: String, status: String) {
    Row {
        Text(
            text = "$label: ",
            color = Color(0xFF8A7BBF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = InterFontFamily
        )
        Text(
            text = status,
            color = Color(0xFF8A7BBF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = InterFontFamily
        )
    }
}

private fun getMentalStatus(score: Int): String {
    return when (score) {
        4 -> "Tenang dan stabil"
        3 -> "Cukup baik"
        2 -> "Sedikit penat"
        1 -> "Sedang berat"
        else -> "-"
    }
}

private fun getPhysicalStatus(score: Int): String {
    return when (score) {
        4 -> "Sangat bugar"
        3 -> "Cukup baik"
        2 -> "Sedikit lelah"
        1 -> "Kurang energi"
        else -> "-"
    }
}

private fun getAcademicStatus(score: Int): String {
    return when (score) {
        4 -> "Sangat produktif"
        3 -> "Cukup fokus"
        2 -> "Kurang fokus"
        1 -> "Sulit konsentrasi"
        else -> "-"
    }
}
