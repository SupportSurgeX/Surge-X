package com.surgex.app.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeWhite
import kotlinx.coroutines.delay

@Composable
fun RoleSelectionScreen(
    onRiderSelected: () -> Unit,
    onDriverSelected: () -> Unit
) {

    var visible by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(250)
        visible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(72.dp))

        Text(
            text = "SurgeX",
            color = SurgeWhite,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(48.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { 80 }
            )
        ) {

            Column {

                Text(
                    text = "How are you\nmoving today?",
                    color = SurgeWhite,
                    fontSize = 38.sp,
                    lineHeight = 43.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Choose your SurgeX experience.",
                    color = SurgeGrey,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { 120 }
            )
        ) {

            Column {

                RoleCard(
                    title = "RIDE",
                    subtitle = "Get where you're going.",
                    label = "Continue as Rider",
                    onClick = onRiderSelected
                )

                Spacer(modifier = Modifier.height(16.dp))

                RoleCard(
                    title = "DRIVE",
                    subtitle = "Turn your time into income.",
                    label = "Continue as Driver",
                    onClick = onDriverSelected
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "SURGEX • MOVE DIFFERENTLY",
            color = Color(0xFF555555),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 28.dp)
        )
    }
}

@Composable
private fun RoleCard(
    title: String,
    subtitle: String,
    label: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurgeSurface
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Text(
                    text = title,
                    color = SurgeWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "↗",
                    color = SurgeWhite,
                    fontSize = 26.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = subtitle,
                color = SurgeGrey,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = label,
                color = SurgeWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
