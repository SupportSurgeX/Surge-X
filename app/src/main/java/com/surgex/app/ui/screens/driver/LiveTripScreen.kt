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
import kotlinx.coroutines.delay

@Composable
fun LiveTripScreen(
    onEndTrip: () -> Unit,
    onSafety: () -> Unit
) {

    var elapsedSeconds by remember {
        mutableIntStateOf(0)
    }

    var distance by remember {
        mutableDoubleStateOf(0.0)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            elapsedSeconds++

            // UI simulation only.
            // Real GPS distance will replace this later.
            if (elapsedSeconds % 10 == 0) {
                distance += 0.1
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {

        TripMap(
            onSafety = onSafety,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        TripPanel(
            elapsedSeconds = elapsedSeconds,
            distance = distance,
            onEndTrip = onEndTrip
        )
    }
}

@Composable
private fun TripMap(
    onSafety: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .background(Color(0xFF181818))
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "LIVE TRIP",
                color = SurgeWhite.copy(alpha = 0.12f),
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "NAVIGATION ACTIVE",
                color = SurgeWhite.copy(alpha = 0.10f),
                fontSize = 9.sp,
                letterSpacing = 3.sp
            )
        }

        Box(
            modifier = Modifier
                .padding(18.dp)
                .size(54.dp)
                .background(
                    SurgeSurfaceLight,
                    CircleShape
                )
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center
        ) {

            TextButton(
                onClick = onSafety,
                contentPadding = PaddingValues(0.dp)
            ) {

                Text(
                    text = "SOS",
                    color = SurgeWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(18.dp),
            color = SurgeBlack.copy(alpha = 0.85f),
            shape = RoundedCornerShape(14.dp)
        ) {

            Column(
                modifier = Modifier.padding(
                    horizontal = 14.dp,
                    vertical = 10.dp
                )
            ) {

                Text(
                    text = "TRIP ACTIVE",
                    color = SurgeWhite,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "Passenger onboard",
                    color = SurgeGrey,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun TripPanel(
    elapsedSeconds: Int,
    distance: Double,
    onEndTrip: () -> Unit
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
                text = "Trip in progress",
                color = SurgeWhite,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Heading to destination",
                color = SurgeGrey,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            TripStats(
                elapsedSeconds = elapsedSeconds,
                distance = distance
            )

            Spacer(modifier = Modifier.height(14.dp))

            DestinationCard()

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onEndTrip,
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
                    text = "END TRIP",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
private fun TripStats(
    elapsedSeconds: Int,
    distance: Double
) {

    val minutes = elapsedSeconds / 60
    val seconds = elapsedSeconds % 60

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        TripStat(
            title = "TIME",
            value = "%02d:%02d".format(minutes, seconds),
            modifier = Modifier.weight(1f)
        )

        TripStat(
            title = "DISTANCE",
            value = "%.1f km".format(distance),
            modifier = Modifier.weight(1f)
        )

        TripStat(
            title = "EST. EARNINGS",
            value = "R92",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun TripStat(
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
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = value,
            color = SurgeWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun DestinationCard() {

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
                .size(11.dp)
                .background(
                    SurgeWhite,
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.width(13.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "DESTINATION",
                color = SurgeGrey,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "Cape Town CBD",
                color = SurgeWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "12.8 km",
            color = SurgeGrey,
            fontSize = 11.sp
        )
    }
}
