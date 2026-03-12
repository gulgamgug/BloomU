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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kelompok3.bloomu.navigation.MissionDetailsRoute
import com.kelompok3.bloomu.navigation.MissionRoute
import com.kelompok3.bloomu.navigation.MissionSelectRoute
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.presentation.mission.components.MissionCTA
import com.kelompok3.bloomu.presentation.mission.components.MissionCategoryMode
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MissionPage(
    modifier: Modifier = Modifier,
    viewModel: MissionViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MissionRoute,
        modifier = modifier
    ) {
        // 1. Halaman Utama Misi (Daftar misi yang sudah diambil)
        composable<MissionRoute> {
            MissionMainScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate(MissionSelectRoute) }
            )
        }
        
        // 2. Halaman Pilih Kategori
        composable<MissionSelectRoute> {
            MissionSelectScreen(
                onBack = { navController.popBackStack() },
                onCategorySelect = { mode ->
                    navController.navigate(MissionDetailsRoute(categoryName = mode.name))
                }
            )
        }
        
        // 3. Halaman Detail Kategori
        composable<MissionDetailsRoute> { backStackEntry ->
            val route: MissionDetailsRoute = backStackEntry.toRoute()
            val mode = MissionCategoryMode.valueOf(route.categoryName)
            
            MissionDetailsScreen(
                mode = mode,
                onBack = { navController.popBackStack() },
                onSubscribe = {
                    viewModel.subscribeMode(mode)
                    // Kembali ke halaman utama setelah subscribe
                    navController.popBackStack(MissionRoute, inclusive = false)
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MissionMainScreen(
    viewModel: MissionViewModel,
    onAddClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val subscribedModes = viewModel.subscribedModes.toList()
    val activeFilters = viewModel.activeFilters
    val allMissions = viewModel.allMissions

    // Hitung progress
    val completedCount = allMissions.count { it.isFinished }
    val totalCount = allMissions.size

    val displayedMissions = if (activeFilters.isEmpty()) {
        allMissions
    } else {
        allMissions.filter { mission ->
            viewModel.activeFilters.any { mode ->
                getMissionDataFromMode(mode).any { it.id == mission.id }
            }
        }
    }

    ShowEllipse(3)

    Box(modifier = Modifier.fillMaxSize()) {
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

            MissionCTA(
                completedCount = completedCount,
                totalCount = totalCount
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFE9E3FF), CircleShape)
                        .clickable { onAddClick() },
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
                    text = "Klik tombol + untuk menambah kategori misi.\nKlik dan tahan kategori untuk menghapusnya.",
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

            if (displayedMissions.isNotEmpty()) {
                displayedMissions.forEach { mission ->
                    OngoingMissions(
                        title = mission.title,
                        description = mission.description,
                        estimation = mission.estimation,
                        isFinished = mission.isFinished,
                        onClick = { viewModel.toggleMission(mission.id) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            } else {
                Text(
                    text = "Daftar misi harianmu kosong.",
                    fontSize = 12.sp,
                    fontFamily = InterFontFamily,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
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
