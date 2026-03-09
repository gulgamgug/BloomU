package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kelompok3.bloomu.ui.theme.BloomUTheme

@Composable
fun sandiBaru(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun sandiBaruPreview() {
    BloomUTheme(dynamicColor = false) {
        sandiBaru()
    }
}
