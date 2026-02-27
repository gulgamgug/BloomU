package com.kelompok3.bloomu.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
    val googleState = supabase.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            when (result) {
                is NativeSignInResult.Success -> onLoginSuccess()
                is NativeSignInResult.Error -> println("Error Google: ${result.message}")
                else -> {}
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logography),
            contentDescription = "Logo"
        )
        Spacer(Modifier.height(10.dp))

        Text("Register Page",
            style = TextStyle(fontSize = 30.sp),
            color = Color(0xFF9383CC)
        )
        Spacer(Modifier.height(15.dp))

        AuthTextField("Nama", nama, { nama = it} )
        AuthTextField("Email", email, { email = it} )
        AuthTextField("Password", password, { password = it} )

        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            scope.launch {
                try {
                    supabase.auth.signUpWith(Email) {
                        this.email = email
                        this.password = password
                        data = buildJsonObject { put("name", nama) }
                    }
                    onRegisterSuccess(email)
                } catch (e: Exception) {
                    println("error daftar: ${e.message}")
                }
            }
        }) { Text("Register") }
        TextButton(onClick = onToLoginScreen) { Text("Login") }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            googleState.startFlow()},
            modifier = Modifier.fillMaxWidth()
        ) { Text("Login with Google") }




    }
}