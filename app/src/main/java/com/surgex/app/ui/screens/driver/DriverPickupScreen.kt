package com.surgex.app.ui.screens.driver

import androidx.compose.animation.core.*
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
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun DriverPickupScreen(
    onArrived: () -> Unit,
    onCancel: () -> Unit
) {

    var eta by remember {
        mutableIntStateOf(4)
    }

    LaunchedEffect(Unit) {
        while (eta > 1) {
            kotlinx.coroutines.delay(60000)
            eta--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {

        PickupMap()

        PickupPanel(
            eta = eta,
            onArrived = onArrived,
            onCancel = onCancel
        )
    }
}

@Composable
private fun ColumnScope.PickupMap() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color(0xFF181818)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "NAVIGATION",
                color = SurgeWhite.copy(alpha = 0.12f),
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "ROUTE TO PICKUP",
                color = SurgeWhite.copy(alpha = 0.10f),
                fontSize = 9.sp,
                letterSpacing = 3.sp
            )
        }

        Box(
            modifier = Modifier
                .size(20.dp)
                .background(
                    SurgeWhite,
                    CircleShape
                )
        )
    }
}

@Composable
private fun PickupPanel(
    eta: Int,
    onArrived: () -> Unit,
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
                text = "Heading to pickup",
                color = SurgeWhite,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Passenger is waiting for you",
                color = SurgeGrey,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            PassengerCard()

            Spacer(modifier = Modifier.height(12.dp))

            NavigationInfo(
                eta = eta
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onArrived,
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
                    text = "I'M AT PICKUP",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(9.dp))

            TextButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "CANCEL RIDE",
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
private fun PassengerCard() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SurgeSurface,
                RoundedCornerShape(17.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    Color(0xFF303030),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "R",
                color = SurgeWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.width(13.dp))

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
                text = "⭐ 4.9 • Verified",
                color = SurgeGrey,
                fontSize = 11.sp
            )
        }

        Text(
            text = "12.8 km trip",
            color = SurgeGrey,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun NavigationInfo(
    eta: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        InfoCard(
            title = "ETA",
            value = "$eta min",
            modifier = Modifier.weight(1f)
        )

        InfoCard(
            title = "DISTANCE",
            value = "2.4 km",
            modifier = Modifier.weight(1f)
        )

        InfoCard(
            title = "EARNINGS",
            value = "R92",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String,
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .background(
                SurgeSurface,
                RoundedCornerShape(14.dp)
            )
            .padding(13.dp)
    ) {

        Text(
            text = title,
            color = SurgeGrey,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            color = SurgeWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
