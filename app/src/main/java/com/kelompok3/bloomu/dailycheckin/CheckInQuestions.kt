package com.kelompok3.bloomu.dailycheckin

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val text: String,
    val weight: Int
)
@Serializable
data class Question(
    val id: Int,
    val text: String,
    val domain: Domain,
    val options: List<Option>
)
enum class Domain {MENTAL, FISIK, AKADEMIK}

object CheckInBank {
    val mentalQuestions = listOf(
    Question(1, "Soal...", Domain.MENTAL, listOf(
        Option("A", 4),
        Option("B", 3),
        Option("C", 2),
        Option("D", 1)
    ))
    )

    val physicalQuestions = listOf(
        Question(1, "Soal...", Domain.MENTAL, listOf(
            Option("A", 4),
            Option("B", 3),
            Option("C", 2),
            Option("D", 1)
        ))
    )
    
    val academicQuestions = listOf(
        Question(1, "Soal...", Domain.MENTAL, listOf(
            Option("A", 4),
            Option("B", 3),
            Option("C", 2),
            Option("D", 1)
        )
        )
    )
}
