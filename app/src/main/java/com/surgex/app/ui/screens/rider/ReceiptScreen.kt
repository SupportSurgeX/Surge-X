package com.surgex.app.ui.screens.rider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.domain.fare.FareBreakdown
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun ReceiptScreen(
    fare: FareBreakdown,
    distanceKm: Double,
    durationMinutes: Int,
    paymentMethod: String,
    onDone: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
            .padding(22.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Receipt",
            color = SurgeWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Payment completed",
            color = SurgeGrey,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = SurgeSurface,
            shape = RoundedCornerShape(22.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "SURGEX",
                    color = SurgeWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Trip receipt",
                    color = SurgeGrey,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                ReceiptRow(
                    "Trip ID",
                    "SX-${System.currentTimeMillis().toString().takeLast(8)}"
                )

                ReceiptRow(
                    "Pickup",
                    "Current pickup"
                )

                ReceiptRow(
                    "Destination",
                    "Cape Town CBD"
                )

                ReceiptRow(
                    "Distance",
                    "%.1f km".format(distanceKm)
                )

                ReceiptRow(
                    "Duration",
                    "$durationMinutes min"
                )

                Spacer(modifier = Modifier.height(15.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(15.dp))

                ReceiptRow("Base fare", money(fare.baseFare))
                ReceiptRow("Distance", money(fare.distanceFare))
                ReceiptRow("Time", money(fare.timeFare))
                ReceiptRow("Booking fee", money(fare.bookingFee))

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(12.dp))

                ReceiptRow(
                    "TOTAL",
                    money(fare.total),
                    bold = true
                )

                Spacer(modifier = Modifier.height(18.dp))

                ReceiptRow(
                    "Payment",
                    paymentMethod
                )

                ReceiptRow(
                    "Status",
                    "COMPLETED"
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

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
    }
}

@Composable
private fun ReceiptRow(
    label: String,
    value: String,
    bold: Boolean = false
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = label,
            color = if (bold) SurgeWhite else SurgeGrey,
            fontSize = if (bold) 13.sp else 11.sp,
            fontWeight = if (bold)
                FontWeight.ExtraBold
            else
                FontWeight.Normal
        )

        Text(
            text = value,
            color = SurgeWhite,
            fontSize = if (bold) 15.sp else 11.sp,
            fontWeight = if (bold)
                FontWeight.ExtraBold
            else
                FontWeight.Medium
        )
    }
}

private fun money(value: Double): String {
    return "R${"%.2f".format(value)}"
}
