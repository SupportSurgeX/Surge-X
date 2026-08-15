package com.surgex.app.ui.screens.rider

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.ui.components.SurgeXMap
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeSurfaceLight
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun RiderHomeScreen(
    onChooseRide: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {

        // Real OpenStreetMap — Cape Town default
        SurgeXMap(
            modifier = Modifier.fillMaxSize(),
            showUserLocation = true,
            latitude = -33.9249,
            longitude = 18.4241
        )

        TopBar()

        RideRequestSheet(onChooseRide = onChooseRide)
    }
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleButton(text = "☰")
        Text(
            text = "SurgeX",
            color = SurgeWhite,
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold
        )
        CircleButton(text = "●")
    }
}

@Composable
private fun CircleButton(text: String) {
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.72f)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = SurgeWhite, fontSize = 18.sp)
    }
}

@Composable
private fun RideRequestSheet(onChooseRide: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            color = SurgeBlack
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF3A3A3A))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Where to?",
                    color = SurgeWhite,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LocationInput(label = "Pickup location", value = "Current location")

                Spacer(modifier = Modifier.height(10.dp))

                LocationInput(label = "Destination", value = "Search destination")

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Quick destinations",
                    color = SurgeGrey,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickDestination(title = "Home", modifier = Modifier.weight(1f))
                    QuickDestination(title = "Work", modifier = Modifier.weight(1f))
                    QuickDestination(title = "Recent", modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = onChooseRide,
                    modifier = Modifier.fillMaxWidth().height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurgeWhite,
                        contentColor = SurgeBlack
                    )
                ) {
                    Text(
                        text = "CHOOSE A RIDE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun LocationInput(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurgeSurface)
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(SurgeWhite)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(text = label, color = SurgeGrey, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, color = SurgeWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun QuickDestination(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(SurgeSurfaceLight)
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = SurgeWhite, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
