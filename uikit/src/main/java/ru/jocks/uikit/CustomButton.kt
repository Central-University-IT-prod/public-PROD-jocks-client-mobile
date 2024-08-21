package ru.jocks.uikit

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    text: String,
    buttonModifier: Modifier = Modifier.wrapContentSize(),
    textModifier: Modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    enabled: Boolean = true,
    onClick : () -> Unit,
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6B4EFF)),
        modifier = buttonModifier,
        enabled = enabled
    )
    {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = textModifier
        )
    }
}