package com.kelompok3.bloomu.presentation.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.datetime.Month
import kotlinx.datetime.number

@Composable
fun MonthSelector(
    selectedMonth: Month,
    onMonthSelected: (Month) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val months = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )

    Box {
        Box(
            modifier = Modifier
                .background(Color(0xFF2A2567), RoundedCornerShape(25.dp))
                .padding(horizontal = 24.dp, vertical = 10.dp)
                .clickable { expanded = true },
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val currentMonthIndex = selectedMonth.number - 1
                Text(
                    text = months.getOrElse(currentMonthIndex) { "" },
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = InterFontFamily
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = android.R.drawable.arrow_down_float),
                    contentDescription = "Select Month",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            months.forEachIndexed { index, monthName ->
                DropdownMenuItem(
                    text = { Text(monthName, color = Color(0xFF2A2567)) },
                    onClick = {
                        onMonthSelected(Month(index + 1))
                        expanded = false
                    }
                )
            }
        }
    }
}
