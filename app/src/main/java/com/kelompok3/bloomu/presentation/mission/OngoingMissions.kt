package com.kelompok3.bloomu.presentation.mission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.presentation.mission.components.MissionCategoryMode
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun OngoingMissions(
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
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = if (isFinished) Color(0xFF8A7BBF) else Color.White,
        border = if (isFinished) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
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
                        color = Color(0xFF9383CC),
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

// fubgsi untuk mendapatkan data misi dari mode kategori
fun getMissionDataFromMode(mode: MissionCategoryMode): List<DailyMission> {
    return when (mode) {
        MissionCategoryMode.ENERGI -> listOf(
            DailyMission(101, "Stretching 5 menit", "Latih kelenturan tubuh untuk aliran darah lebih lancar.", "Estimasi: 5 mnt", false),
            DailyMission(102, "Minum air cukup", "Cukupi hidrasi agar tubuh tidak mudah lelah.", "Estimasi: 2 mnt", false),
            DailyMission(103, "Jalan kaki sebentar", "Jalan santai untuk menyegarkan otot yang kaku.", "Estimasi: 10 mnt", false),
            DailyMission(104, "Keluar ruangan 5 menit", "Dapatkan udara segar dan sinar matahari langsung.", "Estimasi: 5 mnt", false)
        )
        MissionCategoryMode.FOKUS -> listOf(
            DailyMission(201, "10 menit tanpa distraksi", "Latih fokusmu dengan bekerja tanpa gangguan notifikasi.", "Estimasi: 10 mnt", false),
            DailyMission(202, "Rapikan meja belajar", "Lingkungan yang bersih bantu pikiran lebih jernih.", "Estimasi: 5 mnt", false),
            DailyMission(203, "Selesaikan 1 tugas kecil", "Pilih satu tugas kecil dan kerjakan hingga tuntas.", "Estimasi: 15 mnt", false),
            DailyMission(204, "Matikan notifikasi sementara", "Berikan jeda pada gadget untuk fokus yang lebih dalam.", "Estimasi: 1 mnt", false)
        )
        MissionCategoryMode.KEBIASAAN -> listOf(
            DailyMission(301, "Rapikan tempat tidur", "Memulai hari dengan merapikan ruang pribadimu.", "Estimasi: 3 mnt", false),
            DailyMission(302, "Cuci muka", "Segarkan wajah agar mood lebih siap beraktivitas.", "Estimasi: 2 mnt", false),
            DailyMission(303, "Mandi lebih awal", "Disiplin waktu dimulai dari rutinitas mandi pagi.", "Estimasi: 15 mnt", false),
            DailyMission(304, "Bangun 15 menit lebih awal", "Berikan dirimu waktu lebih untuk menyambut pagi.", "Estimasi: 15 mnt", false)
        )
        MissionCategoryMode.ISTIRAHAT -> listOf(
            DailyMission(401, "Power nap 15 menit", "Tidur singkat untuk me-recharge tenaga yang hilang.", "Estimasi: 15 mnt", false),
            DailyMission(402, "Matikan layar sebelum tidur", "Bantu mata dan otak beristirahat dari cahaya biru.", "Estimasi: 30 mnt", false),
            DailyMission(403, "Tarik napas dalam 3 menit", "Tenangkan sistem saraf dengan pernapasan teratur.", "Estimasi: 3 mnt", false),
            DailyMission(404, "Stretching sebelum tidur", "Lepaskan ketegangan otot sebelum tidur malam.", "Estimasi: 5 mnt", false)
        )
    }
}
