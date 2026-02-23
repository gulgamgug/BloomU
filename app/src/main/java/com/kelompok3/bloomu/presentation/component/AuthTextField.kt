package com.kelompok3.bloomu.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthTextField(placeholder: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        placeholder =({ Text(placeholder)}),
        enabled = true,
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(5.dp)
    )

}