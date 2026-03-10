package com.kelompok3.bloomu.presentation.calendar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun MonthSelector(
    selectedMonth: Month,
    selectedYear: Int = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
    onMonthSelected: (Month) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val fullMonths = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )

    val primaryColor = Color(0xFF2A2567)

    Box {
        // Tombol pemicu (Trigger Button)
        Surface(
            modifier = Modifier
                .clickable { expanded = true },
            color = primaryColor,
            shape = RoundedCornerShape(25.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fullMonths[selectedMonth.number - 1],
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = InterFontFamily
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Pilih Bulan",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        if (expanded) {
            Popup(
                onDismissRequest = { expanded = false },
                alignment = Alignment.Center, // Membuat popup muncul di tengah layar
                properties = PopupProperties(focusable = true)
            ) {
                // Animasi Sederhana (Pop Up effect)
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + scaleIn(
                        initialScale = 0.8f,
                        animationSpec = tween(durationMillis = 300)
                    ),
                    exit = fadeOut() + scaleOut(
                        targetScale = 0.8f
                    )
                ) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color.White,
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .width(300.dp)
                            .border(1.dp, Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Tahun di tengah atas
                            Text(
                                text = selectedYear.toString(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor,
                                fontFamily = InterFontFamily,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            // Grid 4 baris x 3 kolom
                            for (row in 0 until 4) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    for (col in 0 until 3) {
                                        val index = row * 3 + col
                                        val isSelected = selectedMonth.number == index + 1
                                        
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(vertical = 4.dp)
                                                .background(
                                                    color = if (isSelected) primaryColor else Color.White,
                                                    shape = RoundedCornerShape(20.dp)
                                                )
                                                .border(
                                                    width = 1.dp,
                                                    color = primaryColor,
                                                    shape = RoundedCornerShape(20.dp)
                                                )
                                                .clickable {
                                                    onMonthSelected(Month(index + 1))
                                                    expanded = false
                                                }
                                                .padding(vertical = 10.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = fullMonths[index],
                                                color = if (isSelected) Color.White else primaryColor,
                                                fontSize = 14.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                fontFamily = InterFontFamily
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MonthSelectorPreview() {
    BloomUTheme {
        Box(modifier = Modifier.padding(20.dp)) {
            MonthSelector(
                selectedMonth = Month.JANUARY,
                onMonthSelected = {}
            )
        }
    }
}
