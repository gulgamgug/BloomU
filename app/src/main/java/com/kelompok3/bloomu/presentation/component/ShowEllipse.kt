package com.kelompok3.bloomu.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelompok3.bloomu.R

@Composable
fun ShowEllipse(mode: Int){
    if (mode == 0) {
        val gradientBackground = Brush.linearGradient(
            colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF)
            )
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {
            // Ellipse kanan atas
            Image(
                painter = painterResource(id = R.drawable.ellipse_1),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(700.dp)
                    .offset(x = 100.dp, y = (-100).dp)
                    .alpha(1.0f)
                    .blur(45.dp)
            )

            // Ellipse kiri bawah
            Image(
                painter = painterResource(id = R.drawable.ellipse_1),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(700.dp)
                    .offset(x = (-100).dp, y = 100.dp)
                    .rotate(180f)
                    .alpha(1.0f)
                    .blur(45.dp)
            )
        }
    } else if (mode == 1) {
        val gradientBackground = Brush.linearGradient(
            colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {
            // Ellipse kanan atas
            Image(
                painter = painterResource(id = R.drawable.ellipse_2),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(700.dp)
                    .offset(x = 100.dp, y = (-100).dp)
                    .alpha(1.0f)
                    .blur(45.dp)
            )

            // Ellipse kiri bawah
            Image(
                painter = painterResource(id = R.drawable.ellipse_2),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(700.dp)
                    .offset(x = (-100).dp, y = 100.dp)
                    .rotate(180f)
                    .alpha(1.0f)
                    .blur(45.dp)
            )
        }
    } else if (mode == 2) {
        val gradientBackground = Brush.linearGradient(
            colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {
            // Ellipse kanan atas
            Image(
                painter = painterResource(id = R.drawable.ellipse_2),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(700.dp)
                    .offset(x = -150.dp, y = (-100).dp)
                    .alpha(1.0f)
                    .blur(45.dp)
            )

            // Ellipse kiri bawah
            Image(
                painter = painterResource(id = R.drawable.ellipse_2),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(700.dp)
                    .offset(x = (150).dp, y = 100.dp)
                    .rotate(180f)
                    .alpha(1.0f)
                    .blur(45.dp)
            )
        }
    }


}

@Composable
@Preview
fun PreviewEllipse(){
    ShowEllipse(2)
}