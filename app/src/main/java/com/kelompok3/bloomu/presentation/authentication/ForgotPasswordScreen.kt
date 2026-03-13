package com.kelompok3.bloomu.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.AuthTextField
import com.kelompok3.bloomu.presentation.component.LoadingDialog
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current

    // ViewModel
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AuthEvent.ResetPasswordEmailSent -> {
                    Toast.makeText(context, "Email link reset kata sandi telah dikirim!", Toast.LENGTH_LONG).show()
                    onBackToLogin()
                }
                is AuthEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    ForgotPasswordContent(
        email = viewModel.email,
        isLoading = viewModel.isLoading,
        onEmailChange = { viewModel.onEmailChange(it) },
        onResetPassword = { viewModel.resetPassword() },
        onBackToLogin = onBackToLogin
    )
}

@Composable
fun ForgotPasswordContent(
    email: String,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onResetPassword: () -> Unit,
    onBackToLogin: () -> Unit
) {
    LoadingDialog(isLoading = isLoading)

    ShowEllipse(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Lupa Kata Sandi?",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF9383CC)
        )
        Spacer(Modifier.height(10.dp))

        Image(
            painter = painterResource(id = R.drawable.logography),
            contentDescription = "Logo"
        )
        Spacer(Modifier.height(40.dp))

        Text(
            text = "Masukkan email kamu untuk mendapatkan link pengaturan ulang kata sandi.",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Medium
            ),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(30.dp))

        Text(
            text = "Email",
            modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 4.dp),
            color = Color(0xFF9383CC),
            fontFamily = InterFontFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
            )
        AuthTextField(
            placeholder = "Email",
            value = email,
            onValueChange = onEmailChange,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.email),
                    contentDescription = "email icon"
                )
            }
        )

        Spacer(Modifier.height(40.dp))
        Button(
            modifier = Modifier.fillMaxWidth().height(53.dp),
            onClick = onResetPassword,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF221E52),
                contentColor = Color.White)
        ) { Text("Kirim Link", fontFamily = InterFontFamily, fontWeight = FontWeight.Bold) }

        Spacer(Modifier.height(20.dp))

        val footerText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontFamily = InterFontFamily, fontWeight = FontWeight.Bold)) {
                append("Kembali ke ")
            }
            withStyle(style = SpanStyle(fontFamily = InterFontFamily, color = Color(0xFF0D47A1), fontWeight = FontWeight.Bold)) {
                append("Masuk")
            }
        }

        TextButton(onClick = onBackToLogin) {
            Text(text = footerText)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    BloomUTheme {
        ForgotPasswordContent(
            email = "user@example.com",
            isLoading = false,
            onEmailChange = {},
            onResetPassword = {},
            onBackToLogin = {}
        )
    }
}
