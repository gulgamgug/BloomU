package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.AuthTextField
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Composable

fun RegisterScreen(onRegisterSuccess: (String) -> Unit, onToLoginScreen: () -> Unit, onLoginSuccess: () -> Unit){
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val googleState = supabase.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            when (result) {
                is NativeSignInResult.Success -> onLoginSuccess()
                is NativeSignInResult.Error -> println("Error Google: ${result.message}")
                else -> {}
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                .blur(35.dp)
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
                .blur(35.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Selamat Datang Kembali di",
                style = TextStyle(fontSize = 20.sp),
                color = Color(0xFF9383CC),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(17.dp))

            Image(
                painter = painterResource(id = R.drawable.logography),
                contentDescription = "Logo"
            )
            Spacer(Modifier.height(25.dp))

            Text(
                text = "Buat Akun",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black,
                textAlign = TextAlign.Start
            )

            Spacer(Modifier.height(15.dp))
            Text(
                text = "Nama",
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 4.dp),
                color = Color(0xFF9383CC),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            AuthTextField(
                placeholder = "Nama",
                value = nama,
                onValueChange = { nama = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.username),
                        contentDescription = "username icon"
                    )
                }

            )
            Spacer(Modifier.height(15.dp))
            Text(
                text = "Email",
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 4.dp),
                color = Color(0xFF9383CC),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            AuthTextField(
                placeholder = "Email",
                value = email,
                onValueChange = { email = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = "email icon"
                    )
                }
            )
            Spacer(Modifier.height(15.dp))
            Text(
                text = "Password",
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 4.dp),
                color = Color(0xFF9383CC),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            )
            AuthTextField(
                placeholder = "Password",
                value = password,
                onValueChange = { password = it },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.gembok),
                        contentDescription = "password icon"
                    )
                }
            )

            Spacer(Modifier.height(30.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    scope.launch {
                        try {
                            supabase.auth.signUpWith(Email) {
                                this.email = email
                                this.password = password
                                data = buildJsonObject { put("nama", nama) }
                            }
                            onRegisterSuccess(email)
                        } catch (e: Exception) {
                            println("error daftar: ${e.message}")
                        }
                    }
                }
            ) { Text("Register") }

            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick = { googleState.startFlow() },
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, Color(0xFF3155AA)),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.googleicon),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(10.dp))
                Text("Sign up with Google", color = Color(0xFF3155AA))
            }

            Spacer(Modifier.height(1.dp))

            val footerText = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Sudah memiliki akun? ")
                }
                withStyle(style = SpanStyle(color = Color(0xFF0D47A1), fontWeight = FontWeight.Bold)) {
                    append("Masuk")
                }
            }

            TextButton(onClick = onToLoginScreen) {
                Text(text = footerText)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    BloomUTheme {
//        RegisterScreen(onRegisterSuccess = {}, onToLoginScreen = {})
//    }
//}