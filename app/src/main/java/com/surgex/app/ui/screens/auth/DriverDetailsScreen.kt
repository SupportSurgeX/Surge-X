package com.surgex.app.ui.screens.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.auth.AuthControllerUpdated
import com.surgex.app.auth.AuthResult
import com.surgex.app.auth.DriverProfile
import kotlinx.coroutines.launch

@Composable
fun DriverDetailsScreen(
    authController: AuthControllerUpdated,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var licenseNumber by remember { mutableStateOf("") }
    var licenseExpiry by remember { mutableStateOf("") }
    var carBrand by remember { mutableStateOf("") }
    var carModel by remember { mutableStateOf("") }
    var carColor by remember { mutableStateOf("") }
    var carYear by remember { mutableStateOf("") }
    var carCategory by remember { mutableStateOf("Sedan") }
    var licensePlate by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val carCategories = listOf("Sedan", "SUV", "Minibus", "Truck", "Van", "Hatchback")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF050505))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = "Driver Details",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Complete your driver information for verification.",
                color = Color(0xFF888888),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(36.dp))

            // License Section
            Text(
                text = "License Information",
                color = Color(0xFF76FF03),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            SurgeXTextField(
                value = licenseNumber,
                onValueChange = { licenseNumber = it },
                label = "License Number",
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(12.dp))

            SurgeXTextField(
                value = licenseExpiry,
                onValueChange = { licenseExpiry = it },
                label = "Expiry Date (DD/MM/YYYY)",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Vehicle Section
            Text(
                text = "Vehicle Information",
                color = Color(0xFF76FF03),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            SurgeXTextField(
                value = carBrand,
                onValueChange = { carBrand = it },
                label = "Brand (e.g., Toyota)",
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(12.dp))

            SurgeXTextField(
                value = carModel,
                onValueChange = { carModel = it },
                label = "Model",
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(12.dp))

            SurgeXTextField(
                value = carYear,
                onValueChange = { carYear = it },
                label = "Year (e.g., 2022)",
                keyboardType = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(12.dp))

            SurgeXTextField(
                value = carColor,
                onValueChange = { carColor = it },
                label = "Color",
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(12.dp))

            SurgeXTextField(
                value = licensePlate,
                onValueChange = { licensePlate = it },
                label = "License Plate",
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Car Category Dropdown
            Text("Vehicle Category", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF1A1A1A)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(carCategory, color = Color.White)
                    DropdownMenu(
                        expanded = false,
                        onDismissRequest = { }
                    ) {
                        carCategories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = { carCategory = category }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            errorMessage?.let {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1A0000)
                ) {
                    Text(
                        text = it,
                        color = Color(0xFFFF4444),
                        fontSize = 13.sp,
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    when {
                        licenseNumber.isBlank() -> errorMessage = "License number required."
                        carBrand.isBlank() -> errorMessage = "Car brand required."
                        carModel.isBlank() -> errorMessage = "Car model required."
                        carYear.isBlank() -> errorMessage = "Car year required."
                        licensePlate.isBlank() -> errorMessage = "License plate required."
                        else -> {
                            isLoading = true
                            scope.launch {
                                val driverProfile = DriverProfile(
                                    licenseNumber = licenseNumber,
                                    licenseExpiry = licenseExpiry,
                                    carBrand = carBrand,
                                    carModel = carModel,
                                    carColor = carColor,
                                    carYear = carYear,
                                    carCategory = carCategory,
                                    licensePlate = licensePlate,
                                    verificationStatus = "PENDING"
                                )
                                when (val result = authController.saveDriverProfile(driverProfile)) {
                                    is AuthResult.Success -> onSuccess()
                                    is AuthResult.Error -> {
                                        errorMessage = result.message
                                        isLoading = false
                                    }
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF76FF03),
                    disabledContainerColor = Color(0xFF1A1A1A)
                ),
                enabled = !isLoading
            ) {
                Text(
                    text = if (isLoading) "SAVING..." else "SAVE DRIVER DETAILS",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
