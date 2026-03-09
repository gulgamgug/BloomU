package com.kelompok3.bloomu.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.InterFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    namaUser: String,
    email: String,
){
    Box(
        Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                // bar atas
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            // panggil teks dari strings.xml
                            text = stringResource(id = R.string.profile_title), 
                            fontFamily = InterFontFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF403959)
                            )
                    },

                    )
            }
        ) { innerPadding ->
            Column(
                Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    namaUser,
                    fontFamily = InterFontFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF403959)
                )
                Text(
                    email,
                    fontFamily = InterFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF8A7BBF)
                )
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF221E52)),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.height(53.dp).width(224.dp)
                ) {
                    Text(
                        text = "Edit Akun",
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    ProfilePage("Hessi", "hessi@mail.com")
}
