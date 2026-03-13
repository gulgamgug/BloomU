package com.kelompok3.bloomu.presentation.mission.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MissionCTA(
    completedCount: Int,
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 197.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    "Selesaikan misi harianmu!",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    lineHeight = 26.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Jaga pola hidup yang sehat dan capai hidup yang lebih produktif!",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 20.sp
                )
                Spacer(Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = CircleShape)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "$completedCount/$totalCount misi telah selesai!",
                        fontSize = 12.sp,
                        color = Color(0xFFB8A4FF),
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.workout),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 10.dp)
                    .size(140.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MissionCTAPreview() {
    BloomUTheme {
        Box(modifier = Modifier.padding(20.dp)) {
            MissionCTA(completedCount = 1, totalCount = 3)
        }
    }
}