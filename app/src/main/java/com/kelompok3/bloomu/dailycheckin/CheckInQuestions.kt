package com.kelompok3.bloomu.dailycheckin

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val text: String,
    val weight: Int // Bobot 1-4
)

@Serializable
data class Question(
    val id: Int,
    val text: String,
    val domain: Domain,
    val options: List<Option>
)

enum class Domain { MENTAL, FISIK, AKADEMIK }

object CheckInBank {
    // Bank soal MENTAL
    val mentalQuestions = listOf(
        Question(1, "Hari ini pikiranmu terasa...", Domain.MENTAL, listOf(
            Option("Ringan & tenang", 4),
            Option("Sedikit penuh", 3),
            Option("Banyak overthinking", 2),
            Option("Sangat kacau", 1)
        )),
        Question(2, "Seberapa sering kamu merasa cemas hari ini?", Domain.MENTAL, listOf(
            Option("Tidak sama sekali", 4),
            Option("Jarang", 3),
            Option("Cukup sering", 2),
            Option("Hampir sepanjang hari", 1)
        )),
        Question(3, "Apakah kamu merasa mampu mengendalikan emosimu hari ini?", Domain.MENTAL, listOf(
            Option("Sangat mampu", 4),
            Option("Cukup mampu", 3),
            Option("Agak sulit", 2),
            Option("Tidak sama sekali", 1)
        ))
    )

    // Bank soal FISIK
    val physicalQuestions = listOf(
        Question(101, "Bagaimana kondisi energimu hari ini?", Domain.FISIK, listOf(
            Option("Sangat bugar", 4),
            Option("Cukup baik", 3),
            Option("Mudah lelah", 2),
            Option("Sangat lemas", 1)
        )),
        Question(102, "Apakah ada bagian tubuh yang terasa tegang/sakit?", Domain.FISIK, listOf(
            Option("Tidak ada", 4),
            Option("Sedikit pegal", 3),
            Option("Cukup mengganggu", 2),
            Option("Sakit sekali", 1)
        ))
    )

    // Bank soal AKADEMIK
    val academicQuestions = listOf(
        Question(201, "Seberapa fokus kamu belajar hari ini?", Domain.AKADEMIK, listOf(
            Option("Sangat fokus", 4),
            Option("Cukup fokus", 3),
            Option("Sering teralihkan", 2),
            Option("Tidak bisa fokus", 1)
        )),
        Question(202, "Apakah kamu merasa tertekan dengan tugas/ujian?", Domain.AKADEMIK, listOf(
            Option("Sangat santai", 4),
            Option("Bisa menangani", 3),
            Option("Cukup stres", 2),
            Option("Sangat tertekan", 1)
        ))
    )
}
