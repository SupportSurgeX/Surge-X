package com.surgex.app.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.surgex.app.ui.theme.SurgeSurfaceLight
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun PassengerVerificationScreen(
    onStartTrip: () -> Unit,
    onCancel: () -> Unit
) {

    var verified by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {

        VerificationMap(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        VerificationPanel(
            verified = verified,
            onVerify = {
                verified = true
            },
            onStartTrip = onStartTrip,
            onCancel = onCancel
        )
    }
}

@Composable
private fun VerificationMap(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .background(Color(0xFF181818)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "PICKUP",
                color = SurgeWhite.copy(alpha = 0.12f),
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "DRIVER ARRIVED",
                color = SurgeWhite.copy(alpha = 0.10f),
                fontSize = 9.sp,
                letterSpacing = 3.sp
            )
        }
    }
}

@Composable
private fun VerificationPanel(
    verified: Boolean,
    onVerify: () -> Unit,
    onStartTrip: () -> Unit,
    onCancel: () -> Unit
) {

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
                horizontal = 22.dp,
                vertical = 22.dp
            )
        ) {

            Text(
                text = "Passenger pickup",
                color = SurgeWhite,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Confirm the correct passenger before starting the trip.",
                color = SurgeGrey,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            PassengerIdentityCard(
                verified = verified
            )

            Spacer(modifier = Modifier.height(18.dp))

            if (!verified) {

                Button(
                    onClick = onVerify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurgeWhite,
                        contentColor = SurgeBlack
                    )
                ) {

                    Text(
                        text = "VERIFY PASSENGER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

            } else {

                Button(
                    onClick = onStartTrip,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurgeWhite,
                        contentColor = SurgeBlack
                    )
                ) {

                    Text(
                        text = "START TRIP",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "CANCEL",
                    color = SurgeGrey,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
private fun PassengerIdentityCard(
    verified: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SurgeSurface,
                RoundedCornerShape(18.dp)
            )
            .padding(17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(52.dp)
                .background(
                    SurgeSurfaceLight,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "R",
                color = SurgeWhite,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "Rider",
                color = SurgeWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "⭐ 4.9 • Verified account",
                color = SurgeGrey,
                fontSize = 11.sp
            )
        }

        if (verified) {

            Box(
                modifier = Modifier
                    .background(
                        SurgeSurfaceLight,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 7.dp
                    )
            ) {

                Text(
                    text = "VERIFIED",
                    color = SurgeWhite,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}
