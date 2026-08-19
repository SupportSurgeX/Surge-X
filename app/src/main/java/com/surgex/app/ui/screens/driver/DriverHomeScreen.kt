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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeSurfaceLight
import com.surgex.app.ui.theme.SurgeWhite
import org.osmdroid.views.MapView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint

@Composable
fun DriverHomeScreen(
    onOnlineChanged: (Boolean) -> Unit = {},
    onRideRequest: () -> Unit = {},
    onSwitchToRider: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var online by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().background(SurgeBlack)
    ) {
        DriverHeader(online = online, onSwitchToRider = onSwitchToRider, onBack = onBack)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            MapFoundation()
        }

        DriverControlPanel(
            online = online,
            onToggleOnline = {
                online = !online
                onOnlineChanged(online)
            },
            onRideRequest = onRideRequest
        )
    }
}

@Composable
private fun MapFoundation() {
    val context = LocalContext.current
    
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            MapView(it).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                controller.setZoom(15.0)
                // Default to Cape Town, South Africa
                controller.setCenter(GeoPoint(-33.9249, 18.4241))
            }
        },
        update = { mapView ->
            // Update map as needed
            mapView.invalidate()
        }
    )
}

@Composable
private fun DriverHeader(online: Boolean, onSwitchToRider: () -> Unit, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A))
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "←", fontSize = 18.sp, color = SurgeWhite)
        }

        Column {
            Text(text = "SurgeX", color = SurgeWhite, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Text(
                text = if (online) "ONLINE" else "OFFLINE",
                color = if (online) SurgeWhite else SurgeGrey,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }

        // Switch to Rider mode
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A))
                .clickable { onSwitchToRider() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🧍", fontSize = 18.sp)
        }
    }
}

@Composable
private fun DriverControlPanel(online: Boolean, onToggleOnline: () -> Unit, onRideRequest: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF0A0A0A),
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 22.dp)) {
            Text(
                text = if (online) "You're ready to earn." else "You're offline.",
                color = SurgeWhite,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = if (online) "We'll notify you when a ride request arrives."
                else "Go online when you're ready to receive rides.",
                color = SurgeGrey,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = onToggleOnline,
                modifier = Modifier.fillMaxWidth().height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (online) SurgeSurfaceLight else SurgeWhite,
                    contentColor = if (online) SurgeWhite else SurgeBlack
                )
            ) {
                Text(
                    text = if (online) "GO OFFLINE" else "GO ONLINE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SummaryCard(title = "TODAY", value = "R0.00", modifier = Modifier.weight(1f))
                SummaryCard(title = "TRIPS", value = "0", modifier = Modifier.weight(1f))
                SummaryCard(title = "ONLINE", value = "0m", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(18.dp))
            
            // Driver Details & Navigation Button
            if (online) {
                Button(
                    onClick = onRideRequest,
                    modifier = Modifier.fillMaxWidth().height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5FF),
                        contentColor = SurgeBlack
                    )
                ) {
                    Text(
                        text = "MANAGE PROFILE",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(title: String, value: String, modifier: Modifier) {
    Column(
        modifier = modifier.background(SurgeSurface, RoundedCornerShape(15.dp)).padding(14.dp)
    ) {
        Text(text = title, color = SurgeGrey, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = value, color = SurgeWhite, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
    }
}
