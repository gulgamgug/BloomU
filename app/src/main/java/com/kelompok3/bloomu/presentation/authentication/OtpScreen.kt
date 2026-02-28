package com.kelompok3.bloomu.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.supabase.supabase
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun OtpScreen(email: String, onVerifSuccess: () -> Unit) {
    val context = LocalContext.current
    var otpCode by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val gradientBackground = Brush.linearGradient(
        colors = listOf(Color(0xFFFFFFFF), Color(0xFFE9E3FF))
    )

    var timerSeconds by remember { mutableStateOf(60) }
    LaunchedEffect(timerSeconds) {
             if (timerSeconds > 0) {
                 delay(1000L)
                 timerSeconds--
             }
        }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)

    ) {
        // Ellipse kanan atas
//        Image(
//            painter = painterResource(id = R.drawable.ellipse_1),
//            contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .size(700.dp)
//                .offset(x = 100.dp, y = (-100).dp)
//                .alpha(1.0f)
//                .blur(35.dp)
//        )
//
//        // Ellipse kiri bawah
//        Image(
//            painter = painterResource(id = R.drawable.ellipse_1),
//            contentDescription = null,
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .size(700.dp)
//                .offset(x = (-100).dp, y = 100.dp)
//                .rotate(180f)
//                .alpha(1.0f)
//                .blur(35.dp)
//        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(120.dp))
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
                color = Color(0xFF000000)
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Masukkan kode OTP yang telah dikirimkan melalui $email",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold),
                color = Color(0xFF262234),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(70.dp))
        }

        Spacer(Modifier.height(25.dp))
        OtpInputField(digitCount = 6, onOtpComplete = { otpCode = it })
        Spacer(Modifier.height(25.dp))
        Button(onClick = {
            scope.launch {
                try {
                    supabase.auth.verifyEmailOtp(
                        type = OtpType.Email.EMAIL,
                        email = email,
                        token = otpCode
                    )
                    onVerifSuccess()
                } catch (e: AuthRestException) {
                    Toast.makeText(context, "Kode OTP tidak valid", Toast.LENGTH_SHORT).show()
                } catch (e: HttpRequestException) {
                    Toast.makeText(context, "Kesalahan jaringan", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Terjadi kesalahan verifikasi", Toast.LENGTH_SHORT).show()
                    println("error verifikasi: ${e.message}")
                }
            }
        }) { Text("Verifikasi Kode", fontFamily = InterFontFamily) }

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
                color = if (timerSeconds == 0) Color(0xFF3155AA) else Color(0xFFBDBDBD),
                modifier = Modifier.clickable(enabled = timerSeconds == 0) {
                    scope.launch {
                        try {
                            supabase.auth.resendEmail(
                                type = OtpType.Email.EMAIL,
                                email = email
                            )
                            timerSeconds = 60
                            Toast.makeText(context, "OTP berhasil dikirim ulang!", Toast.LENGTH_SHORT).show()
                        } catch (e: AuthRestException) {
                            Toast.makeText(context, "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                        } catch (e: RestException) {
                            Toast.makeText(context, "Kesalahan server: ${e.message}", Toast.LENGTH_SHORT).show()
                        } catch (e: HttpRequestException) {
                            Toast.makeText(context, "Kesalahan jaringan", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
        if (timerSeconds > 0) {
            Spacer(Modifier.height(8.dp))
            Text(
                "00:${timerSeconds.toString().padStart(2, '0')}",
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


@Composable
fun OtpInputField(
     digitCount: Int = 6,
     onOtpComplete: (String) -> Unit
 ) {
     // Simpan 6 digit di dalam List
     val otpValues = remember { mutableStateListOf("", "", "", "", "", "") }
     // FocusRequester untuk masing-masing box
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
                         // Logika Pindah Focus ke Kanan
                         if (newValue.isNotEmpty() && index < digitCount - 1) {
                             focusRequesters[index + 1].requestFocus()
                         }
                     }

                     // Cek kalau sudah terisi semua
                     val combinedCode = otpValues.joinToString("")
                     if (combinedCode.length == digitCount) {
                         onOtpComplete(combinedCode)
                     }
                 },
                 modifier = Modifier
                     .width(50.dp)
                     .height(90.dp)
                     .border(
                         width = 1.dp,
                         brush = gradientBrush,
                         shape = RoundedCornerShape(10.dp)
                     )
                     .focusRequester(focusRequesters[index])
                     .onKeyEvent { event ->
                         // Logika Backspace (Pindah Focus ke Kiri)
                         if (event.key == Key.Backspace && otpValues[index].isEmpty() && index > 0) {
                             focusRequesters[index - 1].requestFocus()
                             true
                         } else false
                     },
                 colors = OutlinedTextFieldDefaults.colors(
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
@Preview
fun OtpScreenPreview() {
    OtpScreen("yayaya@gmail.com", onVerifSuccess = {} )}