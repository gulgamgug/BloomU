package com.kelompok3.bloomu.presentation.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.presentation.component.ItemType
import com.kelompok3.bloomu.presentation.component.SettingsItem
import com.kelompok3.bloomu.presentation.mission.MissionViewModel
import com.kelompok3.bloomu.presentation.profile.components.LogoutConfirmationDialog
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    onEditAccountClick: () -> Unit = {},
    onLogOutSuccess: () -> Unit = {},
    viewModel: ProfileViewModel = viewModel(),
    missionViewModel: MissionViewModel = viewModel() // Tambahkan MissionViewModel
) {
    val darkBlue = Color(0xFF231F40) //
    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        //buat minta izin notif
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.toggleNotification(true, context)
        } else {
            Toast.makeText(context, "Izin notifikasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        //kalo log out biar keluar aplikasi
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is ProfileEvent.LogoutSuccess -> {
                    missionViewModel.clearDataOnLogout() // Bersihkan data misi
                    onLogOutSuccess()
                }
                is ProfileEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                viewModel.logout()
            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFE9E3FF)
                    )
                )
            )
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Profil",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = darkBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFFFEBF2), shape = RoundedCornerShape(100.dp))
            )
            
            Box(
                modifier = Modifier
                    .size(110.dp)
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
                    .offset(x = (-25).dp, y = (-25).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mdi_edit_circle),
                    contentDescription = "Edit Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // nama email
        Text(
            text = viewModel.userName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = darkBlue
        )
        Text(
            text = viewModel.userEmail,
            fontSize = 14.sp,
            color = Color(0xFF8A7BBF),
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // tombol edit akun
        Button(
            onClick = onEditAccountClick,
            modifier = Modifier
                .width(210.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = darkBlue),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Edit Akun",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // settings item
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                "Pengaturan",
                color = Color(0xFF9383CC),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
            )

            SettingsItem(
                title = "Notifikasi Harian",
                icon = painterResource(id = R.drawable.notifications),
                type = ItemType.Switch(
                    isChecked = viewModel.isNotificationEnabled,
                    onCheckedChange = { enabled ->
                        if (enabled) {
                            // cek permission dulu kalo di a13+
                            // karena beda cara minta izinnya sama di a12
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    viewModel.toggleNotification(true, context)
                                }
                            } else {
                                viewModel.toggleNotification(true, context)
                            }
                        } else {
                            viewModel.toggleNotification(false, context)
                        }
                    }
                )
            )

            SettingsItem(
                title = "Keluar Akun",
                icon = painterResource(id = R.drawable.logout),
                type = ItemType.Arrow(onClick = { showLogoutDialog = true })
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePagePreview() {
    BloomUTheme(dynamicColor = false) {
        ProfilePage()
    }
}
