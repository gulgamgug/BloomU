package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MissionPage(
    modifier: Modifier = Modifier,
    viewModel: MissionViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    val streakCount = viewModel.streakCount
    val missions = viewModel.missions
    val categories = viewModel.categories
    val selectedCategory = viewModel.selectedCategory

    ShowEllipse(3)

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .statusBarsPadding() // Pindahkan ke sini agar padding ikut ter-scroll
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Judul "Misi" di tengah atas
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

            // Subjudul "Kategori" di kiri
            Text(
                text = "Kategori",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Button Kategori (Rounded 2 berjejer)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Button(
                        onClick = { viewModel.onCategorySelected(category) },
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color(0xFF9383CC) else Color(0xFFE9E3FF)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = category,
                            color = Color.Black,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontFamily = InterFontFamily
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Subjudul "Misi Harian Kamu!"
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

            // Mission List
            missions.forEach { mission ->
                MissionItem(
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

@Composable
fun MissionItem(
    title: String,
    description: String,
    estimation: String,
    isFinished: Boolean,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isFinished) 0.dp else 4.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable{ onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (isFinished) Color(0xFF8A7BBF) else Color.White,
        border = if (isFinished) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Checklist Icon
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isFinished) Color(0xFFE8F5E9) else Color.Transparent,
                        shape = CircleShape
                    )
                    .border(
                        width = 2.dp,
                        color = if (isFinished) Color(0xFF4CAF50) else Color.Gray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isFinished) {
                    Text(
                        text = "✓",
                        color = Color(0xFF9383CC), // Warna ungu button sesuai permintaan
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = if (isFinished) Color.White else Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    color = if (isFinished) Color.White.copy(alpha = 0.9f) else Color.Black.copy(alpha = 0.7f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = InterFontFamily,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = estimation,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = if (isFinished) Color.White else Color(0xFF9383CC),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = InterFontFamily
                )
            }
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
