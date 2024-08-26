package com.example.lingosnack.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.VisualTransformation
import com.example.lingosnack.ui.theme.PalePurple

@Composable
fun AppTextField(
    value: String,
    label: String,
    placeHolder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onTextChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = { text ->
            onTextChange(text)
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolder) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedPlaceholderColor = PalePurple,
            unfocusedPlaceholderColor = PalePurple,
            focusedLabelColor = PalePurple,
            unfocusedLabelColor = PalePurple,
            focusedBorderColor = PalePurple,
            unfocusedBorderColor = PalePurple
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}
