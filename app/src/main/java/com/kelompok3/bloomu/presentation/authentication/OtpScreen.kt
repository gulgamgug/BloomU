package com.kelompok3.bloomu.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OtpScreen(
    email: String,
    onVerifSuccess: () -> Unit,
    viewModel: OtpViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val gradientBackground = Brush.linearGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is OtpEvent.Success -> onVerifSuccess()
                is OtpEvent.Error -> {
                    // Toast dihapus sesuai permintaan
                }
                is OtpEvent.ResendSuccess -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.otepe),
                contentDescription = null,
                modifier = Modifier.size(220.dp)
            )
            
            Spacer(Modifier.height(30.dp))
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Kode OTP",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal),
                    color = Color(0xFF6E6299)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    "Masukkan kode OTP yang telah dikirimkan melalui $email",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.SemiBold),
                    color = Color(0xFF8A7BBF),
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(Modifier.height(40.dp))

            OtpInputField(
                digitCount = 6, 
                onOtpComplete = { viewModel.onOtpCodeChange(it) },
                isError = viewModel.isError
            )

            if (viewModel.isError) {
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.error_24),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Kode OTP tidak valid",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            
            Spacer(Modifier.height(30.dp))
            
            Button(
                onClick = { viewModel.verifyOtp(email) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF221E52)),
            ) { 
                Text(
                    "Verifikasi Kode",
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Tidak menerima kode? ",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = Color(0xFFBDBDBD)
                )
                Text(
                    "Kirim ulang",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    color = if (viewModel.timerSeconds == 0) Color(0xFF3155AA) else Color(0xFFBDBDBD),
                    modifier = Modifier.clickable(enabled = viewModel.timerSeconds == 0) {
                        viewModel.resendOtp(email)
                    }
                )
            }
            if (viewModel.timerSeconds > 0) {
                Spacer(Modifier.height(8.dp))
                Text(
                    "00:${viewModel.timerSeconds.toString().padStart(2, '0')}",
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = Color(0xFFBDBDBD)
                )
            }
        }
    }
}

@Composable
fun OtpInputField(
    digitCount: Int = 6,
    onOtpComplete: (String) -> Unit,
    isError: Boolean = false
) {
    val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { List(digitCount) { FocusRequester() } }

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF5C6EC), Color(0xFF8366EB))
    )

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        otpValues.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otpValues[index] = newValue
                        if (newValue.isNotEmpty() && index < digitCount - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    }
                    val combinedCode = otpValues.joinToString("")
                    onOtpComplete(combinedCode)
                },
                modifier = Modifier
                    .width(50.dp)
                    .height(90.dp)
                    .border(
                        width = 1.dp,
                        brush = if (isError) androidx.compose.ui.graphics.SolidColor(Color.Red) else gradientBrush,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { event ->
                        if (event.key == Key.Backspace && otpValues[index].isEmpty() && index > 0) {
                            focusRequesters[index - 1].requestFocus()
                            true
                        } else false
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold),
                singleLine = true
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OtpScreenPreview() {
    BloomUTheme(dynamicColor = false) {
        OtpScreen("yayaya@gmail.com", onVerifSuccess = {})
    }
}
