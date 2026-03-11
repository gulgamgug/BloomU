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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun StreakSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Runtutan Api",
            color = Color(0xFF2A2567),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = InterFontFamily
        )
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(20.dp), clip = false)
                .background(Color.White, RoundedCornerShape(20.dp))
                .border(1.dp, Color(0xFFF0F0F0).copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.apistrek),
                    contentDescription = "Streak Icon",
                    modifier = Modifier.size(60.dp)
                )
                Text(
                    text = "4 Hari",
                    color = Color(0xFFFF9800),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (1..7).forEach { day ->
                        StreakItem(day, day <= 4)
                    }
                }
            }
        }
    }
}

@Composable
fun StreakItem(day: Int, isActive: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(42.dp)
                .height(28.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.auraapi_fix),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                colorFilter = if (!isActive) ColorFilter.colorMatrix(
                    ColorMatrix().apply { setToSaturation(0f) }
                ) else null,
                alpha = if (isActive) 1f else 0.4f
            )

            Image(
                painter = painterResource(id = R.drawable.apistrek),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                colorFilter = if (!isActive) ColorFilter.colorMatrix(
                    ColorMatrix().apply { setToSaturation(0f) }
                ) else null,
                alpha = if (isActive) 1f else 0.7f
            )
        }

        Text(
            text = "Hari $day",
            fontSize = 10.sp,
            color = if (isActive) Color(0xFFFF9800) else Color.Gray,
            fontFamily = InterFontFamily,
            modifier = Modifier.padding(top = 4.dp),
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}
