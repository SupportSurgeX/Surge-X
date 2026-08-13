package com.surgex.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val SurgeTypography = Typography().run {
    copy(
        headlineLarge = headlineLarge.copy(
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold
        ),
        headlineMedium = headlineMedium.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        ),
        titleLarge = titleLarge.copy(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        bodyLarge = bodyLarge.copy(
            fontSize = 16.sp
        )
    )
}
