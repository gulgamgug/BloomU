package com.kelompok3.bloomu.dailycheckin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun CheckInScreen(
    onFinished: () -> Unit,
    onBack: () -> Unit,
    viewModel: CheckInViewModel = viewModel()
) {
    // Navigasi internal antar Step di dalam satu Screen
    when (viewModel.currentStep) {
        1 -> {
            MoodPage(
                onMoodSelected = { moodId ->
                    viewModel.selectedMoodEmoji = moodId
                    viewModel.currentStep = 2
                }
            )
        }
        2 -> {
            QuestionsStep(
                viewModel = viewModel,
                onBack = { viewModel.currentStep = 1 },
                onNext = { viewModel.currentStep = 3 }
            )
        }
        3 -> {
            MiniDiaryPage(
                viewModel = viewModel,
                onBack = { viewModel.currentStep = 2 },
                onNext = {
                    // Nanti di sini submit ke Supabase lalu onFinished()
                    onFinished()
                }
            )
        }
    }
}

fun getMoodInfo(moodId: Int): Pair<Int, String> {
    return when (moodId) {
        1 -> Pair(R.drawable.emoji5, "Sangat Buruk")
        2 -> Pair(R.drawable.emoji4, "Buruk")
        3 -> Pair(R.drawable.emoji3, "Biasa")
        4 -> Pair(R.drawable.emoji2, "Baik")
        5 -> Pair(R.drawable.emoji_1, "Sangat Baik")
        else -> Pair(R.drawable.emoji3, "Biasa")
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionsStep(
    viewModel: CheckInViewModel = viewModel(),
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val blueOutline = Color(0xFF2A2567)
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
    )

    Box(modifier = Modifier.fillMaxSize()) {
        ShowEllipse(4)
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(40.dp)
                                .background(
                                    Color(0xFF2A2567),
                                    shape = androidx.compose.foundation.shape.CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        //emot dan teks
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Spacer(Modifier.height(4.dp))
                            val moodInfo = getMoodInfo(viewModel.selectedMoodEmoji)
                            Image(
                                painter = painterResource(id = moodInfo.first),
                                contentDescription = null,
                                modifier = Modifier.size(45.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = moodInfo.second,
                                fontFamily = InterFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = blueOutline
                            )
                        }
                    }
                }
            },
            bottomBar = {
                // 4. Tombol Lanjutkan (Alignment Kanan)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onNext,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF221E52)),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(
                            text = "Lanjutkan",
                            fontFamily = InterFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(Modifier.height(8.dp))

                // --- Bagian Pertanyaan ---
                viewModel.activeQuestions.forEach { question ->
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = question.text,
                            fontFamily = InterFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF2A2567)
                        )

                        when (question.domain) {
                            Domain.MENTAL, Domain.AKADEMIK -> {
                                // 2. Card dengan Radio Button (Mental & Akademik)
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    border = BorderStroke(1.dp, gradientBrush),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(3.dp)) {
                                        question.options.forEach { option ->
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(40.dp)
                                            ) {
                                                RadioButton(
                                                    selected = viewModel.userAnswers[question.id] == option.weight,
                                                    onClick = {
                                                        viewModel.userAnswers =
                                                            viewModel.userAnswers.toMutableMap()
                                                                .apply {
                                                                    put(question.id, option.weight)
                                                                }
                                                    },
                                                    colors = RadioButtonDefaults.colors(
                                                        selectedColor = blueOutline
                                                    )

                                                )
                                                Text(
                                                    text = option.text,
                                                    fontFamily = InterFontFamily,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Domain.FISIK -> {
                                // 3. Choice Chips 2x2 (Fisik)
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    maxItemsInEachRow = 2
                                ) {
                                    question.options.forEach { option ->
                                        val isSelected =
                                            viewModel.userAnswers[question.id] == option.weight
                                        FilterChip(
                                            selected = isSelected,
                                            onClick = {
                                                viewModel.userAnswers =
                                                    viewModel.userAnswers.toMutableMap().apply {
                                                        put(question.id, option.weight)
                                                    }
                                            },
                                            label = {
                                                Text(
                                                    text = option.text,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    textAlign = TextAlign.Center,
                                                    fontFamily = InterFontFamily,
                                                    fontSize = 12.sp
                                                )
                                            },
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(58.dp)
                                                .padding(5.dp),
                                            border = BorderStroke(1.dp, Color(0xFF9383CC)),
                                            shape = RoundedCornerShape(50.dp),
                                            colors = FilterChipDefaults.filterChipColors(
                                                containerColor = Color.White,
                                                selectedContainerColor = Color(0xFF9383CC).copy(
                                                    alpha = 0.3f
                                                ),
                                                labelColor = Color.Black,
                                                selectedLabelColor = blueOutline
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(100.dp)) // Beri ruang agar tidak tertutup tombol bawah
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionsStepPreview() {
    QuestionsStep()
}
//
//@Composable
//@Preview