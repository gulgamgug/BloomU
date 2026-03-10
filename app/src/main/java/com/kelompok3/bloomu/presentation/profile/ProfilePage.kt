package com.kelompok3.bloomu.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.R
import com.kelompok3.bloomu.ui.theme.BloomUTheme
import androidx.compose.ui.graphics.Brush

import com.kelompok3.bloomu.presentation.component.ItemType
import com.kelompok3.bloomu.presentation.component.SettingsItem

@Composable
fun ProfilePage(
    modifier: Modifier = Modifier,
    onEditAccountClick: () -> Unit = {}
) {
    val darkBlue = Color(0xFF231F40) // Warna tombol dan teks profil
    var isNotificationEnabled by remember { mutableStateOf(true) }

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

        // Judul Profil
        Text(
            text = "Profil",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = darkBlue,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Foto Profil dengan Grid Background
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

        // Nama & Email
        Text(
            text = "Revi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = darkBlue
        )
        Text(
            text = "vincentrevi@student.ub.ac.id",
            fontSize = 14.sp,
            color = Color(0xFF8A7BBF),
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Tombol Edit Akun
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

        // List Menu menggunakan SettingsItem
        Column(modifier = Modifier.fillMaxWidth()) {
            SettingsItem(
                title = "Pengaturan",
                icon = painterResource(id = R.drawable.settings),
                type = ItemType.Arrow(onClick = {})
            )

            SettingsItem(
                title = "Notifikasi Harian",
                icon = painterResource(id = R.drawable.notifications),
                type = ItemType.Switch(
                    isChecked = isNotificationEnabled,
                    onCheckedChange = { isNotificationEnabled = it }
                )
            )

            SettingsItem(
                title = "Keluar Akun",
                icon = painterResource(id = R.drawable.logout),
                type = ItemType.Arrow(onClick = {})
            )

            SettingsItem(
                title = "Tentang",
                icon = painterResource(id = R.drawable.info),
                type = ItemType.Arrow(onClick = {})
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
