package ru.jocks.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun FormField(
    title: String,
    value: String,
    onValueChange: (it: String, valid: Boolean) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    regex : String? = null,
    errorText : String? = null,
) {
    val regexCompiled = regex?.removePrefix("//")?.removeSuffix("/g")?.toRegex()
    val showErrorRegex = !(regexCompiled?.matches(value) ?: true) && value.isNotEmpty()
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it, regexCompiled?.matches(it) ?: true)
                },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            label = { Text(text = title) },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            isError = showErrorRegex,
        )
        if (regex != null && value.isNotEmpty()) {
            if (showErrorRegex) {
                Text(
                    text = errorText ?: "Incorrect value",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}