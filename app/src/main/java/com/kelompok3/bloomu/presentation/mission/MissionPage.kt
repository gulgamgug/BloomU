package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.presentation.mission.components.MissionCTA
import com.kelompok3.bloomu.presentation.mission.components.MissionCategoryMode
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MissionPage(
    modifier: Modifier = Modifier,
    viewModel: MissionViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    val subscribedModes = viewModel.subscribedModes.toList()
    val activeFilters = viewModel.activeFilters
    val staticMissions = viewModel.staticMissions

    // Menggabungkan misi statis dengan misi dari mode yang di-subscribe
    val allMissions = staticMissions + viewModel.subscribedModes.flatMap { getMissionDataFromMode(it) }

    // Memfilter misi berdasarkan kategori yang dipilih (jika ada filter aktif)
    val filteredMissions = if (activeFilters.isEmpty()) {
        allMissions
    } else {
        // Misi statis tetap muncul, misi kategori difilter
        staticMissions + viewModel.subscribedModes
            .filter { activeFilters.contains(it) }
            .flatMap { getMissionDataFromMode(it) }
    }

    ShowEllipse(3)

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Misi",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(24.dp))

            MissionCTA()

            Spacer(modifier = Modifier.height(32.dp))

            // Baris Judul Kategori dan Tombol +
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kategori",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
                
                // Tombol + untuk ke MissionSelectScreen
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFE9E3FF), CircleShape)
                        .clickable { viewModel.navigateTo(MissionScreen.SELECT) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        color = Color(0xFF9383CC),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Chip Kategori (Grid 2x2)
            if (subscribedModes.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    for (i in subscribedModes.indices step 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            subscribedModes.getOrNull(i)?.let { mode ->
                                CategoryChip(
                                    mode = mode,
                                    isSelected = activeFilters.contains(mode),
                                    onClick = { viewModel.toggleFilter(mode) },
                                    onLongClick = { viewModel.unsubscribeMode(mode) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                            if (i + 1 < subscribedModes.size) {
                                subscribedModes.getOrNull(i + 1)?.let { mode ->
                                    CategoryChip(
                                        mode = mode,
                                        isSelected = activeFilters.contains(mode),
                                        onClick = { viewModel.toggleFilter(mode) },
                                        onLongClick = { viewModel.unsubscribeMode(mode) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "Belum ada kategori yang diikuti.",
                    fontSize = 12.sp,
                    fontFamily = InterFontFamily,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Misi Harian Kamu!",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )

            Text(
                text = "Misi kecil untuk menjaga ritmemu",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mission List (Filtered)
            filteredMissions.forEach { mission ->
                OngoingMissions(
                    title = mission.title,
                    description = mission.description,
                    estimation = mission.estimation,
                    isFinished = mission.isFinished,
                    onClick = { viewModel.toggleMission(mission.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryChip(
    mode: MissionCategoryMode,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(40.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF9383CC) else Color.White,
        border = if (isSelected) null else BorderStroke(
            width = 1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
            )
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = mode.label,
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontFamily = InterFontFamily
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MissionPreview() {
    BloomUTheme(dynamicColor = false) {
        MissionPage(modifier = Modifier)
    }
}
