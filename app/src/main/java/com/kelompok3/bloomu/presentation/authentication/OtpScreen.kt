package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.supabase.supabase
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun OtpScreen(email: String, onVerifSuccess: () -> Unit) {
    var otpCode by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Kode OTP sudah kami kirimkan kepada $email. Silahkan cek inbox email anda", style = TextStyle(fontSize = 20.sp), color = Color(0xFF9383CC))
        Spacer(Modifier.height(25.dp))
        OtpInputField(digitCount = 6, onOtpComplete = { otpCode = it })
        Button(onClick = {
            scope.launch {
                try {
                    supabase.auth.verifyEmailOtp(
                        type = OtpType.Email.EMAIL,
                        email = email,
                        token = otpCode
                    )
                    onVerifSuccess()
                } catch (e: Exception) {
                  //  Snackbar() { Text("Kode OTP Tidak Valid")}
                    println("error verifikasi")
                }
            }
        }) { Text("Verifikasi Kode") }
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
                     .width(45.dp)
                     .focusRequester(focusRequesters[index])
                     .onKeyEvent { event ->
                         // Logika Backspace (Pindah Focus ke Kiri)
                         if (event.key == Key.Backspace && otpValues[index].isEmpty() && index > 0) {
                             focusRequesters[index - 1].requestFocus()
                             true
                         } else false
                     },
                 keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                 textStyle = TextStyle(textAlign = TextAlign.Center, fontSize = 20.sp),
                 singleLine = true
             )
         }
     }
 }