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

@Composable
fun profilepage() {
    val darkBlue = Color(0xFF231F40) // Warna tombol dan teks profil
    var isNotificationEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
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

        Spacer(modifier = Modifier.height(32.dp))

        // Foto Profil dengan Grid Background (diwakili Box dekoratif)
        Box(
            modifier = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background grid tipis (bisa menggunakan resource jika ada, di sini saya pakai placeholder)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFFFEBF2), shape = RoundedCornerShape(100.dp)) // Warna pink muda di belakang
            )
            
            // Lingkaran Foto
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

            // Ikon Edit Kecil di pojok kanan bawah foto
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-25).dp, y = (-25).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mdi_edit_circle),
                    contentDescription = "Edit Icon",
                    tint = Color.Unspecified, // Gunakan warna asli dari file XML
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
            onClick = { /* Action Edit */ },
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

        // List Menu
        Column(modifier = Modifier.fillMaxWidth()) {
            ProfileRowItem(
                iconRes = R.drawable.settings,
                title = "Pengaturan",
                onClick = {}
            )
            
            // Row Notifikasi dengan Switch
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.lonceng),
                    contentDescription = null,
                    tint = darkBlue,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Notifikasi Harian",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkBlue,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = isNotificationEnabled,
                    onCheckedChange = { isNotificationEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFFC5BFFF)
                    )
                )
            }

            ProfileRowItem(
                iconRes = R.drawable.logout,
                title = "Keluar Akun",
                onClick = {}
            )

            ProfileRowItem(
                iconRes = R.drawable.info,
                title = "Tentang",
                onClick = {}
            )
        }
    }
}

@Composable
fun ProfileRowItem(
    iconRes: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            tint = Color(0xFF231F40),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF231F40),
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.next), // Menggunakan back.xml tapi diputar 180 derajat untuk jadi panah kanan
            contentDescription = "next",
            tint = Color(0xFF231F40),
            modifier = Modifier
                .size(16.dp)
                .aspectRatio(1f),
            // Jika butuh rotasi manual: modifier.graphicsLayer(rotationZ = 180f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun profilepagePreview() {
    BloomUTheme(dynamicColor = false) {
        profilepage()
    }
}
