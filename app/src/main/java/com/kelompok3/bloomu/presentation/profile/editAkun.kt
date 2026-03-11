package com.kelompok3.bloomu.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editAkun(
    onBack: () -> Unit = {}
) {
    val darkBlue = Color(0xFF231F40)
    val lightPurpleBg = Brush.verticalGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )
    val labelColor = Color(0xFF988BCA)

    var email by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightPurpleBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(33.dp)
                        .shadow(elevation = 4.dp, shape = CircleShape)
                        .clickable { onBack() }
                )

                Text(
                    text = "Edit Akun",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier.size(150.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(Color(0xFFFFEBF2), shape = RoundedCornerShape(100.dp))
                )

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-20).dp, y = (-20).dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mdi_edit_circle),
                        contentDescription = "Edit Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                EditAccountField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Masukkan teks",
                    leadingIconRes = R.drawable.email,
                    labelColor = labelColor,
                    darkBlue = darkBlue
                )

                Spacer(modifier = Modifier.height(12.dp))

                EditAccountField(
                    label = "Nama",
                    value = nama,
                    onValueChange = { nama = it },
                    placeholder = "Masukkan teks",
                    leadingIconRes = R.drawable.username,
                    labelColor = labelColor,
                    darkBlue = darkBlue
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Password",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = labelColor,
                    modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text("Masukkan teks", color = Color(0xFFD4D4D4), fontSize = 13.sp)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.gembok),
                            contentDescription = null,
                            tint = darkBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(id = R.drawable.tampilkan),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x1A000000))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* Save Logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .height(44.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp), spotColor = Color(0xFF221E52)),
                colors = ButtonDefaults.buttonColors(Color(0xFF221E52)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Simpan",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun EditAccountField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIconRes: Int,
    labelColor: Color,
    darkBlue: Color
) {
    Column {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = labelColor,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(placeholder, color = Color(0xFFD4D4D4), fontSize = 13.sp)
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIconRes),
                    contentDescription = null,
                    tint = darkBlue,
                    modifier = Modifier.size(18.dp)
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0x1A000000))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun editAkunPreview() {
    BloomUTheme(dynamicColor = false) {
        editAkun()
    }
}
