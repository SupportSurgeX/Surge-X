package com.surgex.app.ui.screens.rider

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

data class RideOption(
    val name: String,
    val description: String,
    val eta: String,
    val price: String,
    val capacity: String
)

@Composable
fun RideSelectionScreen(
    pickup: String = "Current location",
    destination: String = "Selected destination",
    onBack: () -> Unit = {},
    onConfirmRide: (RideOption) -> Unit = {}
) {

    val rideOptions = remember {
        listOf(
            RideOption(
                name = "SurgeX Go",
                description = "Affordable everyday rides",
                eta = "3 min",
                price = "R85",
                capacity = "1–4"
            ),
            RideOption(
                name = "SurgeX Comfort",
                description = "More space, more comfort",
                eta = "5 min",
                price = "R110",
                capacity = "1–4"
            ),
            RideOption(
                name = "SurgeX XL",
                description = "Extra space for groups",
                eta = "7 min",
                price = "R145",
                capacity = "1–6"
            )
        )
    }

    var selectedRide by remember {
        mutableStateOf(rideOptions.first())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {

        RideSelectionHeader(
            onBack = onBack
        )

        RouteSummary(
            pickup = pickup,
            destination = destination
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Choose your ride",
            color = SurgeWhite,
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 22.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {

            rideOptions.forEach { option ->

                RideOptionCard(
                    option = option,
                    selected = selectedRide == option,
                    onClick = {
                        selectedRide = option
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        RideConfirmationPanel(
            selectedRide = selectedRide,
            onConfirmRide = {
                onConfirmRide(selectedRide)
            }
        )
    }
}

@Composable
private fun RideSelectionHeader(
    onBack: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    SurgeSurface,
                    RoundedCornerShape(14.dp)
                )
                .clickable {
                    onBack()
                },
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "‹",
                color = SurgeWhite,
                fontSize = 30.sp
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Choose your ride",
            color = SurgeWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RouteSummary(
    pickup: String,
    destination: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
    ) {

        RoutePoint(
            label = "PICKUP",
            value = pickup,
            active = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        RoutePoint(
            label = "DESTINATION",
            value = destination,
            active = false
        )
    }
}

@Composable
private fun RoutePoint(
    label: String,
    value: String,
    active: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                SurgeSurface,
                RoundedCornerShape(15.dp)
            )
            .padding(
                horizontal = 15.dp,
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    if (active) SurgeWhite else SurgeGrey,
                    RoundedCornerShape(50)
                )
        )

        Spacer(modifier = Modifier.width(13.dp))

        Column {

            Text(
                text = label,
                color = SurgeGrey,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value,
                color = SurgeWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun RideOptionCard(
    option: RideOption,
    selected: Boolean,
    onClick: () -> Unit
) {

    val background =
        if (selected) Color(0xFF222222)
        else SurgeSurface

    val border =
        if (selected) SurgeWhite
        else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = background
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (selected) 1.dp else 0.dp,
            color = border
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 16.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RideVehicleIcon()

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = option.name,
                        color = SurgeWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "• ${option.capacity}",
                        color = SurgeGrey,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = option.description,
                    color = SurgeGrey,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Pickup in ${option.eta}",
                    color = SurgeWhite.copy(alpha = 0.75f),
                    fontSize = 11.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = option.price,
                    color = SurgeWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "estimated",
                    color = SurgeGrey,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Composable
private fun RideVehicleIcon() {

    Box(
        modifier = Modifier
            .size(52.dp)
            .background(
                SurgeSurfaceLight,
                RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "🚘",
            fontSize = 24.sp
        )
    }
}

@Composable
private fun RideConfirmationPanel(
    selectedRide: RideOption,
    onConfirmRide: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = SurgeBlack
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 22.dp,
                vertical = 16.dp
            )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = selectedRide.name,
                        color = SurgeGrey,
                        fontSize = 11.sp
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = selectedRide.price,
                        color = SurgeWhite,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Text(
                    text = "Cash / Card",
                    color = SurgeGrey,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onConfirmRide,
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
                    text = "REQUEST ${selectedRide.name.uppercase()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}
