package com.kelompok3.bloomu.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

data class FunFact(val text: String, val source: String)

@Composable
fun FunFactCard() {
    val funFacts = remember {
        listOf(
            FunFact("Berjalan kaki selama 10 menit dapat meningkatkan suasana hati dan tingkat energi secara instan.", "Sumber: American Psychological Association"),
            FunFact("Tertawa dapat menurunkan hormon stres seperti kortisol dan meningkatkan hormon kebahagiaan.", "Sumber: Mayo Clinic"),
            FunFact("Mendengarkan musik favorit dapat memicu pelepasan dopamin, zat kimia 'merasa baik' di otak.", "Sumber: Nature Neuroscience"),
            FunFact("Menulis jurnal tentang hal-hal yang disyukuri setiap hari dapat meningkatkan kualitas tidur.", "Sumber: Journal of Psychosomatic Research"),
            FunFact("Paparan sinar matahari pagi membantu mengatur jam biologis tubuh dan meningkatkan produksi serotonin.", "Sumber: Healthline")
        )
    }

    val selectedFact = remember { funFacts.random() }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(16.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(30.dp))
            .background(brush = gradientBrush, shape = RoundedCornerShape(30.dp))
    ) {
        // Box Judul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(
                    color = Color(0xFF534A73),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp, topStart = 30.dp, topEnd = 30.dp)
                )
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Funfact hari ini",
                fontFamily = InterFontFamily,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.amico),
                contentDescription = "Funfact Illustration",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = selectedFact.text,
                    color = Color(0xFF7A709E),
                    fontFamily = InterFontFamily,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = selectedFact.source,
                    color = Color.Gray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FunFactPreview() {
    BloomUTheme {
        FunFactCard()
    }
}
