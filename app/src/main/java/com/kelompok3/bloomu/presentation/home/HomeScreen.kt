package com.kelompok3.bloomu.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.R.drawable
import com.kelompok3.bloomu.presentation.component.FunFactCard
import com.kelompok3.bloomu.presentation.component.MoodChart
import com.kelompok3.bloomu.presentation.component.PerasaanCard
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    onCheckInClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onLogOutSuccess: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeEvent.LogoutSuccess -> onLogOutSuccess()
                is HomeEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    HomeContent(
        namaUser = viewModel.namaUser,
        onCheckInClick = onCheckInClick,
        onNotificationClick = onNotificationClick,
        onLogoutClick = { viewModel.logout() }
    )
}

@Composable
fun HomeContent(
    namaUser: String,
    onCheckInClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        // Background Ellipse
        ShowEllipse(mode = 0)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 40.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hi, $namaUser!",
                        fontFamily = InterFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF000000)
                    )
                    Text(
                        text = "Apa yang sedang kamu rasakan saat ini?",
                        fontFamily = InterFontFamily,
                        fontSize = 12.sp,
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = onNotificationClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lonceng),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Card Input Perasaan
            PerasaanCard(
                onClick = onCheckInClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Analitik",
                    fontFamily = InterFontFamily,
                    color = Color(0xFF2A2567),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(23.dp)
                ) {
                    // Box Kiri (radial gradient streak)
                    BoxWithConstraints(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp)
                            .clip(RoundedCornerShape(24.dp))
                    ) {
                        val widthPx = constraints.maxWidth.toFloat()
                        val heightPx = constraints.maxHeight.toFloat()

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.radialGradient(
                                        0.2f to Color(0xFFFF9800),
                                        0.6f to Color(0xFFFF6D00),
                                        1.0f to Color(0xFFF44336),
                                        center = Offset(x = widthPx * 0.5f, y = heightPx * 1f),
                                        radius = widthPx * 0.6f
                                    ),
                                    shape = RoundedCornerShape(24.dp),
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        text = "Streak Saat Ini",
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontFamily = InterFontFamily
                                    )
                                    Text(
                                        text = "4 Hari",
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = InterFontFamily
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                androidx.compose.foundation.Image(
                                    painter = androidx.compose.ui.res.painterResource(id = drawable.apistrik),
                                    contentDescription = "Streak Icon",
                                    modifier = Modifier.size(45.dp),
                                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                        Color.White
                                    )
                                )
                            }
                        }
                    }

                    // Box Kanan (Solid FCEDF9)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp)
                            .background(
                                color = Color(0xFFFCEDF9),
                                shape = RoundedCornerShape(24.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Total Check-In",
                                    color = Color(0xFF2A2567),
                                    fontSize = 14.sp,
                                    fontFamily = InterFontFamily
                                )
                                Text(
                                    text = "120 Mood",
                                    color = Color(0xFF2A2567),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = InterFontFamily
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // Grafik Mood (Dummy data untuk tampilan)
            MoodChart(moodData = listOf(4f, 3f, 2f, 3.5f, 1f, 0.5f, 2.5f))

            Spacer(modifier = Modifier.height(8.dp))

            // Card Fun Fact
            FunFactCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BloomUTheme {
        HomeContent(
            namaUser = "User",
            onCheckInClick = {},
            onNotificationClick = {},
            onLogoutClick = {}
        )
    }
}
