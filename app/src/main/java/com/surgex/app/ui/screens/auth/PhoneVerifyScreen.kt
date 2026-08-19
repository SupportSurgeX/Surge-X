package com.surgex.app.ui.screens.auth

import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.auth.AuthControllerEnhanced
import kotlinx.coroutines.delay

@Composable
fun PhoneVerifyScreen(
    phoneNumber: String,
    authController: AuthControllerEnhanced,
    onCodeSent: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    var phone by remember { mutableStateOf(phoneNumber) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(80)
        visible = true
    }

    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {
        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-60).dp)
                .scale(pulse)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF00E5FF).copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(50)
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { -20 }
            ) {
                Column {
                    Text(
                        text = "SurgeX",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(56.dp))

                    Text(
                        text = "Verify your\nnumber.",
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 44.sp,
                        letterSpacing = (-1.5).sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "We'll send a 6-digit code to confirm your number.",
                        color = Color(0xFF505050),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(52.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(700, 150)) + slideInVertically(tween(700, 150)) { 50 }
            ) {
                Column {
                    // SA flag hint
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF0D0D0D)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🇿🇦  +27",
                                color = Color(0xFF888888),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "  |",
                                color = Color(0xFF2A2A2A),
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SurgeXTextField(
                        value = phone,
                        onValueChange = { phone = it; errorMessage = null },
                        label = "Phone number (e.g. 0821234567)",
                        keyboardType = KeyboardType.Phone
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Enter your number without the country code.\nWe'll add +27 automatically.",
                        color = Color(0xFF3A3A3A),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF150000)
                        ) {
                            Text(
                                text = it,
                                color = Color(0xFFFF4444),
                                fontSize = 13.sp,
                                modifier = Modifier.fillMaxWidth().padding(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = {
                            val raw = phone.trim().removePrefix("0")
                            val formatted = "+27$raw"

                            if (raw.length < 9) {
                                errorMessage = "Please enter a valid South African number."
                                return@Button
                            }

                            isLoading = true
                            errorMessage = null

                            authController.sendOtp(
                                phoneNumber = formatted,
                                activity = activity,
                                onCodeSent = {
                                    isLoading = false
                                    onCodeSent()
                                },
                                onAutoVerified = {
                                    isLoading = false
                                    onCodeSent()
                                },
                                onError = { msg ->
                                    isLoading = false
                                    errorMessage = msg
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(58.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            disabledContainerColor = Color(0xFF1C1C1C)
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.Black,
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Text(
                                text = "SEND CODE",
                                color = Color.Black,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "← Back",
                        color = Color(0xFF303030),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().clickable { onBack() }
                    )
                }
            }
        }

        Text(
            text = "SURGEX • MOVE DIFFERENTLY",
            color = Color(0xFF181818),
            fontSize = 9.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 3.sp,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
        )
    }
}
