package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

enum class MissionCategoryMode(
    val label: String,
    val iconRes: Int,
    val gradientColors: List<Color>,
    val description: String
) {
    ENERGI(
        "Energi",
        R.drawable.energi,
        listOf(Color(0xFFFDD835), Color(0xFF978120)),
        "Kategori Energi membantu meningkatkan energi tubuh dan membuat tubuh terasa lebih segar. Kategori ini membantu mengurangi rasa lelah dan meningkatkan semangat beraktivitas."
    ),
    ISTIRAHAT(
        "Istirahat",
        R.drawable.istirahat,
        listOf(Color(0xFFFB8C00), Color(0xFF955300)),
        "Kategori Pola Tidur membantu tubuh dan pikiran mendapatkan waktu istirahat yang cukup. Kategori ini membantu mengurangi kelelahan dan membuat tubuh lebih rileks."
    ),
    FOKUS(
        "Fokus",
        R.drawable.fokus,
        listOf(Color(0xFF82AFFE), Color(0xFF4D6998)),
        "Kategori Fokus membantu meningkatkan konsentrasi dan menjaga pikiran tetap terarah. Dengan menjaga fokus, aktivitas belajar atau bekerja dapat dilakukan dengan lebih efektif dan produktif."
    ),
    KEBIASAAN(
        "Kebiasaan Kecil",
        R.drawable.kebiasaan,
        listOf(Color(0xFF16EB07), Color(0xFF0D8504)),
        "Kategori Kebiasaan Kecil membantu membangun perubahan positif melalui tindakan sederhana yang dilakukan secara konsisten dalam kehidupan sehari-hari."
    )
}

@Composable
fun CategoryCard(
    mode: MissionCategoryMode,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .height(180.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = mode.gradientColors)
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = mode.iconRes),
                    contentDescription = mode.label,
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mode.label,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    BloomUTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CategoryCard(mode = MissionCategoryMode.ENERGI, modifier = Modifier.weight(1f))
                CategoryCard(mode = MissionCategoryMode.ISTIRAHAT, modifier = Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CategoryCard(mode = MissionCategoryMode.FOKUS, modifier = Modifier.weight(1f))
                CategoryCard(mode = MissionCategoryMode.KEBIASAAN, modifier = Modifier.weight(1f))
            }
        }
    }
}
