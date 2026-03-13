package com.kelompok3.bloomu.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kelompok3.bloomu.ui.theme.InterFontFamily

sealed class ItemType {
    data class Arrow(val onClick: () -> Unit) : ItemType()
    data class Switch(val isChecked: Boolean, val onCheckedChange: (Boolean) -> Unit) : ItemType()
}

/**
 * komponen buat list item di page profil.
 * tampilannya bisa diatur mau pake panah atau switch.
 *
 * @param title teks judul yang ditampilin di tengah.
 * @param icon gambar icon yang posisinya di paling kiri.
 * @param type jenis aksi itemnya. pake [ItemType.Arrow] kalo mau itemnya bisa
 * diklik buat pindah halaman, atau [ItemType.Switch] buat toggle on/off.
 */
@Composable
fun SettingsItem(
    title: String,
    icon: Painter,
    type: ItemType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = type is ItemType.Arrow) {
                if (type is ItemType.Arrow) type.onClick()
            }
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            tint = Color(0xFF221E52),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF221E52),
            modifier = Modifier.weight(1f)
        )

        when (type) {
            is ItemType.Arrow -> {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "selanjutnya",
                    tint = Color(0xFF201A3D)
                )
            }
            is ItemType.Switch -> {
                Switch(
                    checked = type.isChecked,
                    onCheckedChange = type.onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFFC4B5FD)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsItemPreview() {
    Column {
        SettingsItem(
            title = "Pengaturan",
            icon = rememberVectorPainter(image = Icons.Default.Settings),
            type = ItemType.Arrow(
                onClick = {}
            )
        )

        SettingsItem(
            title = "Notifikasi Harian",
            icon = rememberVectorPainter(image = Icons.Default.Notifications),
            type = ItemType.Switch(
                isChecked = true,
                onCheckedChange = {}
            )
        )
    }
}