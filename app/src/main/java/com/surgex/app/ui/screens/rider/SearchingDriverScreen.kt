package com.surgex.app.ui.screens.rider

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
fun SearchingDriverScreen(
    onDriverFound: () -> Unit,
    onCancel: () -> Unit
) {

    var elapsedSeconds by remember {
        mutableIntStateOf(0)
    }

    val infiniteTransition = rememberInfiniteTransition(
        label = "driverSearch"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.82f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            elapsedSeconds++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {

        /* MAP / DRIVER RADAR */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(0xFF181818)),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(210.dp)
                    .scale(pulse)
                    .background(
                        SurgeWhite.copy(alpha = 0.04f),
                        CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(
                        SurgeWhite.copy(alpha = 0.06f),
                        CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(
                        SurgeWhite,
                        CircleShape
                    )
            )
        }

        /* SEARCH PANEL */

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurgeBlack,
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 24.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(4.dp)
                        .background(
                            Color(0xFF3A3A3A),
                            RoundedCornerShape(50)
                        )
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "Finding your driver",
                    color = SurgeWhite,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Searching nearby SurgeX drivers...",
                    color = SurgeGrey,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = SurgeSurface,
                    shape = RoundedCornerShape(18.dp)
                ) {

                    Row(
                        modifier = Modifier.padding(
                            horizontal = 18.dp,
                            vertical = 16.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    SurgeWhite,
                                    CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "Searching",
                                color = SurgeWhite,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Expanding nearby driver radius",
                                color = SurgeGrey,
                                fontSize = 11.sp
                            )
                        }

                        Text(
                            text = "${elapsedSeconds}s",
                            color = SurgeGrey,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "We're looking for the closest available driver.",
                    color = SurgeGrey,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = SurgeWhite
                    )
                ) {

                    Text(
                        text = "CANCEL REQUEST",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
