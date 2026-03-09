package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun sandiBaru() {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFF3EFFF))
    )
    val textColorDark = Color(0xFF6B5E9E)
    val textColorLight = Color(0xFF988BCA)
    val buttonColor = Color(0xFF201D40)

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        // Ellipse 1 kanan atas dengan efek blur
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
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            // Title
            Text(
                text = "Masukkan Kata Sandi Baru",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = textColorDark
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Subtitle
            Text(
                text = "Silakan masukkan Kata Sandi baru Anda",
                style = TextStyle(
                    fontFamily = InterFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = textColorLight
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Form Section
            Column(modifier = Modifier.fillMaxWidth()) {
                
                // --- Input 1: Kata Sandi ---
                Text(
                    text = "Kata sandi",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColorLight,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )
                
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(
                            text = "Masukkan teks",
                            color = Color(0xFFD4D4D4),
                            fontSize = 14.sp,
                            fontFamily = InterFontFamily
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.gembok),
                            contentDescription = "Lock Icon",
                            tint = buttonColor,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(id = R.drawable.tampilkan), // Menggunakan icon eye/tampilkan
                                contentDescription = "Toggle Visibility",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorDark
                    ),
                    shape = RoundedCornerShape(24.dp), // Bentuk oval
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x1A000000))
                )

                Spacer(modifier = Modifier.height(24.dp))

                // --- Input 2: Konfirmasi Kata Sandi ---
                Text(
                    text = "Konfirmasi kata sandi",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = textColorLight,
                    modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                )
                
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = {
                        Text(
                            text = "Masukkan teks",
                            color = Color(0xFFD4D4D4),
                            fontSize = 14.sp,
                            fontFamily = InterFontFamily
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.gembok),
                            contentDescription = "Lock Icon",
                            tint = buttonColor,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                            Icon(
                                painter = painterResource(id = R.drawable.tampilkan),
                                contentDescription = "Toggle Visibility",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorDark
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x1A000000))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Tombol Konfirmasi di paling bawah
            Button(
                onClick = { /* Handle Confirmation */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Konfirmasi",
                    fontFamily = InterFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun sandiBaruPreview() {
    BloomUTheme(dynamicColor = false) {
        sandiBaru()
    }
}
