package com.kelompok3.bloomu.presentation.notifikasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
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

@Composable
fun notifikasi() {
    val scrollState = rememberScrollState()
    val lightPurpleBg = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )
    val purpleText = Color(0xFF403959)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightPurpleBg)
    ) {
        // --- BACKGROUND LAYER (Bloom Effect) ---
        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = "ellipse",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(1000.dp)
                .offset(y = (-450).dp)
                .rotate(0f)
                .alpha(0.7f)
                .blur(100.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ellipse_1),
            contentDescription = "ellipse_2",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(800.dp)
                .offset(y = (-350).dp)
                .rotate(90f)
                .alpha(0.5f)
                .blur(80.dp)
        )

        // --- CONTENT LAYER ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Header Row
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(33.dp)
                        .clickable { /* Back Logic */ }
                )

                Text(
                    text = "Notifikasi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = purpleText,
                    fontFamily = InterFontFamily,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Section: Hari ini
            NotificationSection("Hari ini")
            NotificationItem(
                title = "Bagaimana perasaanmu hari ini?",
                description = "Masukkan perasaanmu hari ini dan nyalakan streak harianmu!",
                iconRes = R.drawable.vector__6_ // Streak/Flame icon
            )
            NotificationItem(
                title = "Kamu belum minum air hari ini",
                description = "Luangkan waktumu untuk minum 8 gelas air dalam sehari agar tubuhmu selalu fit!",
                iconRes = R.drawable.mingcute_time_line // Clock icon
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section: Kemarin
            NotificationSection("Kemarin")
            NotificationItem(
                title = "Bagaimana perasaanmu hari ini?",
                description = "Masukkan perasaanmu hari ini dan nyalakan streak harianmu!",
                iconRes = R.drawable.vector__6_
            )
            NotificationItem(
                title = "Kamu belum minum air hari ini",
                description = "Luangkan waktumu untuk minum 8 gelas air dalam sehari agar tubuhmu selalu fit!",
                iconRes = R.drawable.mingcute_time_line
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section: Minggu lalu
            NotificationSection("Minggu lalu")
            NotificationItem(
                title = "Bagaimana perasaanmu hari ini?",
                description = "Masukkan perasaanmu hari ini dan nyalakan streak harianmu!",
                iconRes = R.drawable.vector__6_
            )
            NotificationItem(
                title = "Kamu belum minum air hari ini",
                description = "Luangkan waktumu untuk minum 8 gelas air dalam sehari agar tubuhmu selalu fit!",
                iconRes = R.drawable.mingcute_time_line
            )
            NotificationItem(
                title = "Bagaimana perasaanmu hari ini?",
                description = "Masukkan perasaanmu hari ini dan nyalakan streak harianmu!",
                iconRes = R.drawable.vector__6_
            )
            NotificationItem(
                title = "Kamu belum minum air hari ini",
                description = "Luangkan waktumu untuk minum 8 gelas air dalam sehari agar tubuhmu selalu fit!",
                iconRes = R.drawable.mingcute_time_line
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun NotificationSection(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontFamily = InterFontFamily,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun NotificationItem(title: String, description: String, iconRes: Int, isUnread: Boolean = false) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnread) Color(0xFFF8F9FF) else Color.White // Sedikit kebiruan jika belum dibaca
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp), spotColor = Color(0x40000000))
            .clickable { /* Handle notification click */ }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isUnread) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Red, CircleShape)
                                .padding(end = 8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = if (isUnread) FontWeight.ExtraBold else FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = InterFontFamily
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = Color.Black.copy(alpha = 0.7f),
                    lineHeight = 16.sp,
                    fontFamily = InterFontFamily
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                alpha = 0.6f
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun notifikasiPreview() {
    BloomUTheme(dynamicColor = false) {
        notifikasi()
    }
}
