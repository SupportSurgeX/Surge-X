package com.surgex.app.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.auth.AuthController
import com.surgex.app.auth.AuthResult
import com.surgex.app.auth.UserRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    role: UserRole,
    authController: AuthController,
    onRegisterSuccess: (phone: String) -> Unit,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var visible by remember { mutableStateOf(false) }

    val roleLabel = if (role == UserRole.RIDER) "RIDER" else "DRIVER"
    val roleAccent = if (role == UserRole.RIDER) Color(0xFF00E5FF) else Color(0xFF76FF03)

    LaunchedEffect(Unit) {
        delay(80)
        visible = true
    }

    val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 0.85f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-50).dp)
                .scale(pulse)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            roleAccent.copy(alpha = 0.05f),
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
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { -30 }
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "SurgeX",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = roleAccent.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = roleLabel,
                                color = roleAccent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(52.dp))

                    Text(
                        text = "Create\naccount.",
                        color = Color.White,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 46.sp,
                        letterSpacing = (-1).sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Join the SurgeX network.",
                        color = Color(0xFF555555),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800, 200)) + slideInVertically(tween(800, 200)) { 40 }
            ) {
                Column {
                    SurgeXTextField(
                        value = name,
                        onValueChange = { name = it; errorMessage = null },
                        label = "Full name"
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    SurgeXTextField(
                        value = email,
                        onValueChange = { email = it; errorMessage = null },
                        label = "Email address",
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    SurgeXTextField(
                        value = phone,
                        onValueChange = { phone = it; errorMessage = null },
                        label = "Phone number (e.g. 0821234567)",
                        keyboardType = KeyboardType.Phone
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    SurgeXTextField(
                        value = password,
                        onValueChange = { password = it; errorMessage = null },
                        label = "Password",
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    SurgeXTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it; errorMessage = null },
                        label = "Confirm password",
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    errorMessage?.let {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF1A0000)
                        ) {
                            Text(
                                text = it,
                                color = Color(0xFFFF4444),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.fillMaxWidth().padding(12.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            when {
                                name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank() ->
                                    errorMessage = "Please fill in all fields."
                                password != confirmPassword ->
                                    errorMessage = "Passwords do not match."
                                password.length < 6 ->
                                    errorMessage = "Password must be at least 6 characters."
                                phone.length < 9 ->
                                    errorMessage = "Please enter a valid phone number."
                                else -> {
                                    isLoading = true
                                    scope.launch {
                                        when (val result = authController.register(
                                            name = name.trim(),
                                            email = email.trim(),
                                            phone = phone.trim(),
                                            password = password,
                                            role = role
                                        )) {
                                            is AuthResult.Success -> onRegisterSuccess(phone.trim())
                                            is AuthResult.Error -> {
                                                errorMessage = result.message
                                                isLoading = false
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(58.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            disabledContainerColor = Color(0xFF1A1A1A)
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
                                text = "CREATE ACCOUNT",
                                color = Color.Black,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "← Back to Sign In",
                        color = Color(0xFF333333),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().clickable { onBack() }
                    )

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }

        Text(
            text = "SURGEX • MOVE DIFFERENTLY",
            color = Color(0xFF1A1A1A),
            fontSize = 9.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 3.sp,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
        )
    }
}
