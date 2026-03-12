package com.kelompok3.bloomu.presentation.assessment

import com.kelompok3.bloomu.R

data class AssessmentCard(
    val imageRes: Int,
    val title: String,
    val description: String,
    val microActions: List<String>
)

object AssessmentContentProvider {
    // Logika penampilan card
    fun getAssessmentResult(
        moodScore: Int,
        mentalScore: Int,
        physicalScore: Int,
        academicScore: Int
    ): AssessmentCard {
        // priority system
        return when {
            mentalScore <= 2 -> AssessmentCard(
                imageRes = R.drawable.card_bad_1, // Ganti image yang sesuai nanti (ga jadi ternyata)
                title = "Pikiranmu sedang sedikit penuh",
                description = "Sepertinya ada banyak hal yang membebani pikiranmu hari ini. Tidak apa-apa untuk merasa lelah.",
                microActions = listOf(
                    "Tuliskan 3 hal yang mengganggumu di kertas, lalu remas dan buang.",
                    "Lakukan teknik pernapasan 4-7-8 selama 2 menit.",
                    "Dengarkan lagu instrumental yang menenangkan."
                )
            )
            physicalScore <= 2 -> AssessmentCard(
                imageRes = R.drawable.card_bad_1,
                title = "Tubuhmu butuh istirahat",
                description = "Energimu terlihat rendah hari ini. Tubuhmu sedang memberikan sinyal untuk melambat sejenak.",
                microActions = listOf(
                    "Minum segelas air putih hangat.",
                    "Lakukan peregangan leher dan bahu selama 5 menit.",
                    "Pejamkan mata sejenak tanpa distraksi gawai."
                )
            )
            academicScore <= 2 -> AssessmentCard(
                imageRes = R.drawable.card_good_1,
                title = "Fokusmu sedang terbagi",
                description = "Menyelesaikan tugas memang penting, tapi kesehatan mentalmu jauh lebih utama.",
                microActions = listOf(
                    "Gunakan teknik Pomodoro (25 menit kerja, 5 menit istirahat).",
                    "Rapikan meja belajarmu agar pikiran lebih jernih.",
                    "Jauhi ponsel selama 30 menit ke depan."
                )
            )
            else -> AssessmentCard(
                imageRes = R.drawable.card_good_1,
                title = "Harimu terasa menyenangkan hari ini!",
                description = "Energi dan suasana hatimu hari ini menunjukkan ritme yang lebih seimbang dibanding beberapa hari sebelumnya.",
                microActions = listOf(
                    "Pertahankan ritme baik ini. Luangkan 5-10 menit untuk mensyukuri hal kecil.",
                    "Bagikan energi positifmu dengan memberikan pujian kepada teman.",
                    "Manjakan dirimu dengan camilan favorit sebagai apresiasi."
                )
            )
        }
    }
}
