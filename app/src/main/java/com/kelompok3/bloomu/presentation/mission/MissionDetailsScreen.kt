package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun MissionDetailsScreen(
    viewModel: MissionViewModel
) {
    val mode = viewModel.selectedCategoryMode ?: return
    
    MissionDetailsContent(
        mode = mode,
        onBackClick = { viewModel.navigateTo(MissionScreen.SELECT) }
    )
}

@Composable
fun MissionDetailsContent(
    mode: MissionCategoryMode,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    ShowEllipse(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Top Bar: Back Button dan Judul
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF26215D),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = "Misi",
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Selected CategoryCard (Full Width)
        CategoryCard(
            mode = mode,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Deskripsi Kategori
        Text(
            text = mode.description,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.Black.copy(alpha = 0.7f),
            lineHeight = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Label Aktivitas (Tengah)
        Text(
            text = "Aktivitas",
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4 MissionItems (2x2 Grid)
        val activities = listOf("Aktivitas 1", "Aktivitas 2", "Aktivitas 3", "Aktivitas 4")
        
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            for (i in 0 until activities.size step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MissionItems(
                        title = activities[i],
                        modifier = Modifier.weight(1f)
                    )
                    if (i + 1 < activities.size) {
                        MissionItems(
                            title = activities[i+1],
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Tombol Mulai Misi
        Button(
            onClick = { /* Placeholder */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9383CC)
            )
        ) {
            Text(
                text = "Mulai Misi",
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MissionDetailsScreenPreview() {
    BloomUTheme {
        MissionDetailsContent(
            mode = MissionCategoryMode.ENERGI,
            onBackClick = {}
        )
    }
}
