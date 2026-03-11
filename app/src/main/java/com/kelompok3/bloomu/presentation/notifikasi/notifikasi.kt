package com.kelompok3.bloomu.presentation.notifikasi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kelompok3.bloomu.ui.theme.BloomUTheme

@Composable
fun notifikasi() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier

        ) {

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