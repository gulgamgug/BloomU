package com.kelompok3.bloomu.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import com.kelompok3.bloomu.ui.theme.InterFontFamily
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    onLogOutSuccess: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeEvent.LogoutSuccess -> onLogOutSuccess()
                is HomeEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier =  Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen", fontFamily = InterFontFamily)
        Text("Halo ${viewModel.namaUser}!", fontFamily = InterFontFamily)
        Spacer(Modifier.height(30.dp))
        Button(onClick = {
            viewModel.logout()
        }) { Text("Log Out", fontFamily = InterFontFamily) }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BloomUTheme {
        HomeScreen(onLogOutSuccess = {})
    }
}
