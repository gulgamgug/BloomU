package com.kelompok3.bloomu.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.AuthTextField
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    onRegisterSuccess: (String) -> Unit,
    onToLoginScreen: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var isGoogleLoading by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    val googleState = supabase.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            isGoogleLoading = false
            when (result) {
                is NativeSignInResult.Success -> onLoginSuccess()
                is NativeSignInResult.Error -> {
                    Toast.makeText(context, "Google Error: ${result.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    )

    // Listen to events from ViewModel
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RegisterEvent.Success -> {
                    onRegisterSuccess(event.email)
                }
                is RegisterEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    com.kelompok3.bloomu.presentation.component.LoadingDialog(isLoading = viewModel.isLoading || isGoogleLoading)

    ShowEllipse(0)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text("Selamat Datang di",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xFF9383CC)
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
                    fontFamily = InterFontFamily,
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
                fontFamily = InterFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
            AuthTextField(
                placeholder = "Nama",
                value = viewModel.nama,
                onValueChange = { viewModel.onNamaChange(it) },
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
                fontFamily = InterFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
            AuthTextField(
                placeholder = "Email",
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
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
                fontFamily = InterFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
            AuthTextField(
                placeholder = "Password",
                value = viewModel.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.gembok),
                        contentDescription = "password icon"
                    )
                }
            )

            Spacer(Modifier.height(30.dp))
            Button(
                modifier = Modifier.fillMaxWidth().height(53.dp),
                onClick = {
                    viewModel.signUp()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF221E52),
                    contentColor = Color.White)
            ) { Text("Buat Akun", fontFamily = InterFontFamily, fontWeight = FontWeight.Bold) }

            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick = { 
                    isGoogleLoading = true
                    googleState.startFlow() 
                },
                modifier = Modifier.fillMaxWidth().height(53.dp),
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
                Text("Sign up with Google", color = Color(0xFF3155AA), fontFamily = InterFontFamily)
            }

            Spacer(Modifier.height(1.dp))

            val footerText = buildAnnotatedString {
                withStyle(style = SpanStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Bold)) {
                    append("Sudah memiliki akun? ")
                }
                withStyle(style = SpanStyle(fontFamily = InterFontFamily, color = Color(0xFF0D47A1), fontWeight = FontWeight.Bold)) {
                    append("Masuk")
                }
            }

            TextButton(onClick = onToLoginScreen) {
                Text(text = footerText)
            }
        }

}
