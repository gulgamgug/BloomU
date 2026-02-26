package com.kelompok3.bloomu.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthTextField(
        placeholder: String,
        value: String,
        onValueChange: (String) -> Unit,
        isError: Boolean = false,
        errorMessage: String = ""
) {
    OutlinedTextField(
        value = value,
        placeholder =({ Text(placeholder)}),
        enabled = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        },
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(30.dp)
    )

}