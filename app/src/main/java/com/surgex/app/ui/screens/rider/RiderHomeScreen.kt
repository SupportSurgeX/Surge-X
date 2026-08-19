package com.surgex.app.ui.screens.rider

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeSurfaceLight
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun RiderHomeScreen(
    onChooseRide: () -> Unit,
    onSwitchToDriver: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
    ) {
        MapFoundation()
        TopBar(onSwitchToDriver = onSwitchToDriver, onBack = onBack)
        RideRequestSheet(onChooseRide = onChooseRide)
    }
}

@Composable
private fun MapFoundation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "MAP",
                color = Color.White.copy(alpha = 0.06f),
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "LOCATION READY",
                color = Color.White.copy(alpha = 0.05f),
                fontSize = 10.sp,
                letterSpacing = 3.sp
            )
        }
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(SurgeWhite)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun TopBar(onSwitchToDriver: () -> Unit, onBack: () -> Unit) {
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

        Text(
            text = "SurgeX",
            color = SurgeWhite,
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold
        )

        // Switch to Driver mode
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A1A))
                .clickable { onSwitchToDriver() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🚗", fontSize = 18.sp)
        }
    }
}

@Composable
private fun RideRequestSheet(onChooseRide: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Surface(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            color = Color(0xFF0A0A0A)
        ) {
            Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 20.dp)) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF2A2A2A))
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(22.dp))
                Text(text = "Where to?", color = SurgeWhite, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(16.dp))
                LocationInput(label = "Pickup location", value = "Current location", isClickable = false)
                Spacer(modifier = Modifier.height(10.dp))
                LocationInput(label = "Destination", value = "Search destination", isClickable = true)
                Spacer(modifier = Modifier.height(22.dp))
                Text(text = "Quick destinations", color = SurgeGrey, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    QuickDestination(title = "Home", modifier = Modifier.weight(1f))
                    QuickDestination(title = "Work", modifier = Modifier.weight(1f))
                    QuickDestination(title = "Recent", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = onChooseRide,
                    modifier = Modifier.fillMaxWidth().height(58.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SurgeWhite, contentColor = SurgeBlack)
                ) {
                    Text(text = "CHOOSE A RIDE", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun LocationInput(label: String, value: String, isClickable: Boolean = false) {
    var inputValue by remember { mutableStateOf("") }
    
    if (isClickable) {
        // For destination - allow text input
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF2A2A2A),
                focusedBorderColor = Color(0xFF00E5FF),
                unfocusedContainerColor = SurgeSurface,
                focusedContainerColor = SurgeSurface,
                unfocusedLabelColor = SurgeGrey,
                focusedLabelColor = Color(0xFF00E5FF),
                unfocusedTextColor = SurgeWhite,
                focusedTextColor = SurgeWhite
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true
        )
    } else {
        // For pickup location - read only
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurgeSurface)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(SurgeWhite))
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(text = label, color = SurgeGrey, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = value, color = SurgeWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
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
