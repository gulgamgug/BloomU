package com.kelompok3.bloomu.presentation.authentication

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.AuthTextField
import com.kelompok3.bloomu.presentation.component.LoadingDialog
import com.kelompok3.bloomu.presentation.component.ShowEllipse
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPassword(
    onSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current

    val textColorDark = Color(0xFF6B5E9E)
    val textColorLight = Color(0xFF988BCA)
    val buttonColor = Color(0xFF201D40)

    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AuthEvent.PasswordUpdated -> {
                    Toast.makeText(context, "Password berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    onSuccess()
                }
                is AuthEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    LoadingDialog(isLoading = viewModel.isLoading)

    ShowEllipse(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            //
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
            
            //
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

            // Form
            Column(modifier = Modifier.fillMaxWidth()) {
                
                //
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
                
                AuthTextField(
                    placeholder = "Masukkan teks",
                    value = viewModel.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
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
                                painter = painterResource(id = R.drawable.tampilkan),
                                contentDescription = "Toggle Visibility",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                
                AuthTextField(
                    placeholder = "Masukkan teks",
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
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
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { 
                    if (viewModel.password != confirmPassword) {
                        Toast.makeText(context, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.updatePassword()
                    }
                },
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
