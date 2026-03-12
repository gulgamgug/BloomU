package com.kelompok3.bloomu.presentation.mission.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@Composable
fun mission1() {
    val lightPurpleBg = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightPurpleBg)
    ) {

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Misi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A4A4A),
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Kategori",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = InterFontFamily
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryLargeCard(
                        title = "Energi",
                        iconRes = R.drawable.energi,
                        startColor = Color(0xFFFDD835),
                        endColor = Color(0xFF978120),
                        modifier = Modifier.weight(1f)
                    )
                    CategoryLargeCard(
                        title = "Istirahat",
                        iconRes = R.drawable.istirahat,
                        startColor = Color(0xFFFB8C00),
                        endColor = Color(0xFF955300),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryLargeCard(
                        title = "Fokus",
                        iconRes = R.drawable.fokus,
                        startColor = Color(0xFF82AFFE),
                        endColor = Color(0xFF4D6998),
                        modifier = Modifier.weight(1f)
                    )
                    CategoryLargeCard(
                        title = "Kebiasaan Kecil",
                        iconRes = R.drawable.kebiasaankecil,
                        startColor = Color(0xFF16EB07),
                        endColor = Color(0xFF0D8504),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryLargeCard(
    title: String,
    iconRes: Int,
    startColor: Color,
    endColor: Color,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.verticalGradient(
        colors = listOf(startColor, endColor)
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = title,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = InterFontFamily,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun mission1Preview() {
    BloomUTheme(dynamicColor = false) {
        mission1()
    }
}
