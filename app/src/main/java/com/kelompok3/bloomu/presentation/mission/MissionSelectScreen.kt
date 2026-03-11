package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun MissionSelectScreen(
    viewModel: MissionViewModel
) {
    MissionSelectContent(
        onCategorySelect = { mode -> viewModel.selectCategoryMode(mode) }
    )
}

@Composable
fun MissionSelectContent(
    onCategorySelect: (MissionCategoryMode) -> Unit
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
        
        Text(
            text = "Misi",
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Kategori",
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val modes = MissionCategoryMode.values()
        
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            for (i in 0 until modes.size step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryCard(
                        mode = modes[i],
                        modifier = Modifier.weight(1f),
                        onClick = { onCategorySelect(modes[i]) }
                    )
                    if (i + 1 < modes.size) {
                        CategoryCard(
                            mode = modes[i + 1],
                            modifier = Modifier.weight(1f),
                            onClick = { onCategorySelect(modes[i+1]) }
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MissionSelectScreenPreview() {
    BloomUTheme {
        MissionSelectContent(onCategorySelect = {})
    }
}
