package com.surgex.app.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun DriverHomeScreen(
    onOnlineChanged: (Boolean) -> Unit = {},
    onRideRequest: () -> Unit = {}
) {

    var online by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {

        DriverHeader(
            online = online
        )

        DriverMap()

        DriverControlPanel(
            online = online,
            onToggleOnline = {

                online = !online

                onOnlineChanged(online)
            }
        )
    }
}

@Composable
private fun DriverHeader(
    online: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column {

            Text(
                text = "SurgeX",
                color = SurgeWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = if (online) "ONLINE" else "OFFLINE",
                color = if (online) SurgeWhite else SurgeGrey,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    SurgeSurface,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "●",
                color = if (online) SurgeWhite else SurgeGrey,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun DriverMap() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color(0xFF1B1B1B)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "DRIVER MAP",
                color = SurgeWhite.copy(alpha = 0.12f),
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "LOCATION READY",
                color = SurgeWhite.copy(alpha = 0.10f),
                fontSize = 9.sp,
                letterSpacing = 3.sp
            )
        }

        Box(
            modifier = Modifier
                .size(18.dp)
                .background(
                    SurgeWhite,
                    CircleShape
                )
        )
    }
}

@Composable
private fun DriverControlPanel(
    online: Boolean,
    onToggleOnline: () -> Unit
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
                text = if (online)
                    "You're ready to earn."
                else
                    "You're offline.",
                color = SurgeWhite,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = if (online)
                    "We'll notify you when a ride request arrives."
                else
                    "Go online when you're ready to receive rides.",
                color = SurgeGrey,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onToggleOnline,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (online)
                        SurgeSurfaceLight
                    else
                        SurgeWhite,
                    contentColor = if (online)
                        SurgeWhite
                    else
                        SurgeBlack
                )
            ) {

                Text(
                    text = if (online)
                        "GO OFFLINE"
                    else
                        "GO ONLINE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            EarningsSummary()
        }
    }
}

@Composable
private fun EarningsSummary() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        SummaryCard(
            title = "TODAY",
            value = "R0.00",
            modifier = Modifier.weight(1f)
        )

        SummaryCard(
            title = "TRIPS",
            value = "0",
            modifier = Modifier.weight(1f)
        )

        SummaryCard(
            title = "ONLINE",
            value = "0m",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String,
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .background(
                SurgeSurface,
                RoundedCornerShape(15.dp)
            )
            .padding(14.dp)
    ) {

        Text(
            text = title,
            color = SurgeGrey,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = value,
            color = SurgeWhite,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
