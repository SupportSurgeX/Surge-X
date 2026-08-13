package com.surgex.app.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.domain.fare.FareBreakdown
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun TripSummaryScreen(
    fare: FareBreakdown,
    distanceKm: Double,
    durationMinutes: Int,
    onDone: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
            .verticalScroll(rememberScrollState())
            .padding(22.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        CompletionIcon()

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Trip completed",
            modifier = Modifier.fillMaxWidth(),
            color = SurgeWhite,
            fontSize = 29.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Your ride has been completed successfully.",
            color = SurgeGrey,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        EarningsCard(
            total = fare.total
        )

        Spacer(modifier = Modifier.height(16.dp))

        TripInfoCard(
            distanceKm = distanceKm,
            durationMinutes = durationMinutes
        )

        Spacer(modifier = Modifier.height(16.dp))

        FareBreakdownCard(
            fare = fare
        )

        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = onDone,
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
                text = "DONE",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun CompletionIcon() {

    Box(
        modifier = Modifier
            .size(64.dp)
            .background(
                SurgeSurface,
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "✓",
            color = SurgeWhite,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun EarningsCard(
    total: Double
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SurgeSurface,
                RoundedCornerShape(20.dp)
            )
            .padding(20.dp)
    ) {

        Text(
            text = "TOTAL FARE",
            color = SurgeGrey,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "R${"%.2f".format(total)}",
            color = SurgeWhite,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Driver earnings before platform adjustments",
            color = SurgeGrey,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun TripInfoCard(
    distanceKm: Double,
    durationMinutes: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        InfoCard(
            title = "DISTANCE",
            value = "%.1f km".format(distanceKm),
            modifier = Modifier.weight(1f)
        )

        InfoCard(
            title = "DURATION",
            value = "$durationMinutes min",
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
                RoundedCornerShape(16.dp)
            )
            .padding(15.dp)
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
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun FareBreakdownCard(
    fare: FareBreakdown
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SurgeSurface,
                RoundedCornerShape(20.dp)
            )
            .padding(18.dp)
    ) {

        Text(
            text = "FARE BREAKDOWN",
            color = SurgeGrey,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        FareRow("Base fare", fare.baseFare)

        FareRow("Distance", fare.distanceFare)

        FareRow("Time", fare.timeFare)

        FareRow("Booking fee", fare.bookingFee)

        if (fare.waitingFee > 0) {
            FareRow("Waiting", fare.waitingFee)
        }

        if (fare.tolls > 0) {
            FareRow("Tolls", fare.tolls)
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 12.dp),
            color = Color(0xFF303030)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "TOTAL",
                color = SurgeWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "R${"%.2f".format(fare.total)}",
                color = SurgeWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun FareRow(
    label: String,
    amount: Double
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            color = SurgeGrey,
            fontSize = 12.sp
        )

        Text(
            text = "R${"%.2f".format(amount)}",
            color = SurgeWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
