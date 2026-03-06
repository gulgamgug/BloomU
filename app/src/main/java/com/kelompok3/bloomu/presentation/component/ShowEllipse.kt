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

/**
 * Mode 0 = ellipse kanan atas kiri bawah, bg putih
 *
 * Mode 1 = ellipse kanan atas kiri bawah, bg ungu
 *
 * Mode 2 = ellipse kiri atas kanan bawah, bg ungu
 *
 * Mode 3 = ellipse kiri atas kanan bawah, bg putih
 *
 * Mode 4 = ellipse tengah, bg putih
 */
@Composable
fun ShowEllipse(mode: Int){
    if (mode == 0) {
        val gradientBackground = Brush.linearGradient(
            colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
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
                    .size(400.dp)
                    .offset(x = 80.dp, y = (-80).dp)
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
    } else if (mode ==3) {
        val gradientBackground = Brush.linearGradient(
            colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {
            // Ellipse kiri atas
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-150).dp, y = (-150).dp)
                    .size(500.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF8366EB).copy(alpha = 0.5f), Color.Transparent)
                        )
                    )
            )

            // Ellipse kanan bawah
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 150.dp, y = 150.dp)
                    .size(500.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF8366EB).copy(alpha = 0.5f), Color.Transparent)
                        )
                    )
            )
        }
    } else if (mode == 4) {
        val gradientBackground = Brush.linearGradient(
            colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {
            // Ellipse tengah
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(600.dp)
                    .offset(x = 0.dp, y = -100.dp)
                    .background(
                        brush = Brush.radialGradient(
                            0.2f to Color(0x80A28AFF),
                            0.75f to Color(0x80CABCFF),
                            1.0f to Color(0x00FFFFFF),
                        )
                    )
            )

        }
    }


}

@Composable
@Preview
fun PreviewEllipse(){
    ShowEllipse(3)
}