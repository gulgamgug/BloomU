package com.kelompok3.bloomu.presentation.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kelompok3.bloomu.ui.theme.BloomUTheme

import androidx.compose.foundation.background
import androidx.compose.ui.window.DialogProperties

@Composable
fun CalendarPopUp(
    onDismissRequest: () -> Unit,
    onMonthSelected: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.9f)), // Background hitam (sedikit transparan agar tidak terlalu flat)
            contentAlignment = Alignment.Center
        ) {
            CalendarPopUpContent(onMonthSelected = onMonthSelected)
        }
    }
}

@Composable
fun CalendarPopUpContent(
    onMonthSelected: (String) -> Unit
) {
    val primaryColor = Color(0xFF2A2567)
    val months = listOf(
        "Januari", "Februari", "Maret", "April", "Mei", "Juni",
        "Juli", "Agustus", "September", "Oktober", "November", "Desember"
    )

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Year Text
            Text(
                text = "2026",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Months Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.wrapContentHeight()
            ) {
                items(months) { month ->
                    OutlinedButton(
                        onClick = { onMonthSelected(month) },
                        border = BorderStroke(1.5.dp, primaryColor),
                        shape = RoundedCornerShape(15.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = primaryColor,
                            containerColor = Color.Transparent
                        ),
                        modifier = Modifier.fillMaxWidth().height(45.dp)
                    ) {
                        Text(
                            text = month,
                            fontSize = 12.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarPopUpPreview() {
    BloomUTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CalendarPopUpContent(onMonthSelected = {})
        }
    }
}
