package com.kelompok3.bloomu.presentation.calendar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.presentation.calendar.MoodEntry
import com.kelompok3.bloomu.presentation.dailycheckin.getMoodInfo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.platform.LocalDensity

@Composable
fun CalendarCarousel(
    selectedYear: Int,
    selectedMonth: Month,
    selectedDay: Int,
    maxDayToShow: Int,
    monthlyMoodData: Map<Int, MoodEntry>,
    onDaySelected: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    // Total padding horizontal: 24dp (Box luar) + 20dp (Box dalam) = 44dp per sisi
    val totalSidePadding = 24.dp + 20.dp
    val containerWidthPx = with(density) { (configuration.screenWidthDp.dp - (totalSidePadding * 2)).toPx() }
    val itemWidthPx = with(density) { 36.dp.toPx() }
    
    // Offset = (Setengah Lebar Kontainer) - (Setengah Lebar Item)
    val centerOffset = (containerWidthPx / 2) - (itemWidthPx / 2)

    // Auto-scroll to selected day
    LaunchedEffect(selectedDay) {
        if (selectedDay > 0) {
            listState.animateScrollToItem(
                index = selectedDay - 1,
                scrollOffset = -centerOffset.toInt()
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(130.dp)
            .background(Color.White, RoundedCornerShape(35.dp))
            .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(35.dp))
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items((1..maxDayToShow).toList()) { day ->
                val moodEntry = monthlyMoodData[day]
                val isSelected = selectedDay == day

                // Get day of week name (short)
                val date = try {
                    LocalDate(selectedYear, selectedMonth, day)
                } catch (e: Exception) {
                    null
                }
                
                val dayOfWeek = when (date?.dayOfWeek?.name) {
                    "MONDAY" -> "S"
                    "TUESDAY" -> "S"
                    "WEDNESDAY" -> "R"
                    "THURSDAY" -> "K"
                    "FRIDAY" -> "J"
                    "SATURDAY" -> "S"
                    "SUNDAY" -> "M"
                    else -> ""
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(36.dp)
                        .clickable { onDaySelected(day) }
                ) {
                    Text(
                        text = dayOfWeek,
                        color = Color(0xFF2A2567),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    if (moodEntry != null) {
                        val moodInfo = getMoodInfo(moodEntry.moodScore)
                        Image(
                            painter = painterResource(id = moodInfo.first),
                            contentDescription = moodInfo.second,
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(
                                    if (isSelected) Color(0xFF2A2567) else Color(0xFFE9E3FF),
                                    CircleShape
                                )
                        )
                    }

                    Text(
                        text = "$day",
                        fontSize = 12.sp,
                        color = if (isSelected) Color(0xFF2A2567) else Color.Black,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
