package com.kelompok3.bloomu.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.presentation.calendar.components.CalendarCarousel
import com.kelompok3.bloomu.presentation.calendar.components.MonthSelector
import com.kelompok3.bloomu.presentation.calendar.components.MoodChartSection
import com.kelompok3.bloomu.presentation.calendar.components.MoodDetailCard
import com.kelompok3.bloomu.presentation.calendar.components.StreakSection
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun CalendarPage(
    modifier: Modifier,
    onCheckInClick: () -> Unit = {},
    viewModel: CalendarViewModel = viewModel()
) {
    val distribution = viewModel.getMoodDistribution()
    val totalMoods = distribution.sum()
    
    val today = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val daysCount = viewModel.getDaysCount()
    
    // pembatasan tanggal carousel
    val maxDayToShow = if (viewModel.selectedYear == today.year && viewModel.selectedMonth == today.month) {
        today.dayOfMonth
    } else if (viewModel.selectedYear > today.year || (viewModel.selectedYear == today.year && viewModel.selectedMonth > today.month)) {
        0 
    } else {
        daysCount
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ShowEllipse(mode = 0)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 40.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kalender Mood",
                color = Color(0xFF2A2567),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily,
                modifier = Modifier.padding(top = 24.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            StreakSection()

            Spacer(modifier = Modifier.height(32.dp))

            // chart mood
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Grafik mood kamu",
                    color = Color(0xFF2A2567),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            
            MoodChartSection(
                distribution = distribution,
                totalMoods = totalMoods
            )

            Spacer(modifier = Modifier.height(32.dp))

            MonthSelector(
                selectedMonth = viewModel.selectedMonth,
                onMonthSelected = { newMonth ->
                    viewModel.selectedMonth = newMonth
                    viewModel.fetchMonthlyMoodData()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            CalendarCarousel(
                selectedYear = viewModel.selectedYear,
                selectedMonth = viewModel.selectedMonth,
                selectedDay = viewModel.selectedDay,
                maxDayToShow = maxDayToShow,
                monthlyMoodData = viewModel.monthlyMoodData,
                onDaySelected = { day ->
                    viewModel.selectedDay = day
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            MoodDetailCard(
                selectedDay = viewModel.selectedDay,
                moodEntry = viewModel.monthlyMoodData[viewModel.selectedDay],
                onCheckInClick = onCheckInClick
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun calendarPagePreview() {
    BloomUTheme(dynamicColor = false) {
        CalendarPage(modifier = Modifier)
    }
}
