package com.surgex.app.ui.screens.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SurgeXTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    val isFocused = value.isNotEmpty()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFFFFFFF) else Color(0xFF2A2A2A),
        animationSpec = tween(300),
        label = "border"
    )

    val labelColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFAAAAAA) else Color(0xFF555555),
        animationSpec = tween(300),
        label = "label"
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = labelColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = Color(0xFF2A2A2A),
            focusedContainerColor = Color(0xFF0D0D0D),
            unfocusedContainerColor = Color(0xFF0A0A0A),
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}
