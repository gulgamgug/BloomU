package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.mission.components.MissionCategoryMode
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

data class MissionActivity(
    val iconRes: Int,
    val text: String
)

@Composable
fun MissionItems(
    activity: MissionActivity,
    mode: MissionCategoryMode,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .height(107.dp)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(colors = mode.gradientColors),
                shape = RoundedCornerShape(20.dp)
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = activity.iconRes),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = activity.text,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Black,
                textAlign = TextAlign.Start,
                lineHeight = 16.sp
            )
        }
    }
}

fun getActivitiesForMode(mode: MissionCategoryMode): List<MissionActivity> {
    return when (mode) {
        MissionCategoryMode.ENERGI -> listOf(
            MissionActivity(R.drawable.physical_therapy, "Stretching 5 menit"),
            MissionActivity(R.drawable.local_drink, "Minum air cukup"),
            MissionActivity(R.drawable.barefoot, "Jalan kaki sebentar"),
            MissionActivity(R.drawable.nature_people, "Keluar ruangan 5 menit")
        )
        MissionCategoryMode.FOKUS -> listOf(
            MissionActivity(R.drawable.clock_loader_10, "10 menit tanpa distraksi"),
            MissionActivity(R.drawable.view_comfy_alt, "Rapikan meja belajar"),
            MissionActivity(R.drawable.assignment_turned_in, "Selesaikan 1 tugas kecil"),
            MissionActivity(R.drawable.notifications_off, "Matikan notifikasi sementara")
        )
        MissionCategoryMode.KEBIASAAN -> listOf(
            MissionActivity(R.drawable.bed, "Rapikan tempat tidur"),
            MissionActivity(R.drawable.clean_hands, "Cuci muka"),
            MissionActivity(R.drawable.bathtub, "Mandi lebih awal"),
            MissionActivity(R.drawable.clear_day, "Bangun 15 menit lebih awal")
        )
        MissionCategoryMode.ISTIRAHAT -> listOf(
            MissionActivity(R.drawable.hotel, "Power nap 15 menit"),
            MissionActivity(R.drawable.mobile_hand_off, "Matikan layar 30 menit sebelum tidur"),
            MissionActivity(R.drawable.air, "Tarik napas dalam 3 menit"),
            MissionActivity(R.drawable.physical_therapy__1_, "Stretching sebelum tidur")
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MissionItemsPreview() {
    BloomUTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val sampleMode = MissionCategoryMode.ENERGI
            val activities = getActivitiesForMode(sampleMode)
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MissionItems(activity = activities[0], mode = sampleMode, modifier = Modifier.weight(1f))
                MissionItems(activity = activities[1], mode = sampleMode, modifier = Modifier.weight(1f))
            }
        }
    }
}
