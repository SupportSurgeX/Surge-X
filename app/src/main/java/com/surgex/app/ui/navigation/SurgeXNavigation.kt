package com.surgex.app.ui.navigation

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.surgex.app.auth.AuthController
import com.surgex.app.auth.AuthControllerEnhanced
import com.surgex.app.auth.UserRole
import com.surgex.app.core.trip.SurgeXTripController
import com.surgex.app.domain.payment.PaymentMethod
import com.surgex.app.ui.screens.auth.DriverDetailsScreen
import com.surgex.app.ui.screens.auth.LoginScreen
import com.surgex.app.ui.screens.auth.OtpScreen
import com.surgex.app.ui.screens.auth.PhoneVerifyScreen
import com.surgex.app.ui.screens.auth.RegisterScreen
import com.surgex.app.ui.screens.driver.*
import com.surgex.app.ui.screens.onboarding.RoleSelectionScreen
import com.surgex.app.ui.screens.rider.*
import com.surgex.app.ui.screens.splash.SplashScreen
import kotlinx.coroutines.launch

private enum class SurgeXScreen {
    SPLASH,
    LOGIN,
    REGISTER,
    PHONE_VERIFY,
    OTP_VERIFY,
    RIDER_HOME,
    RIDE_SELECTION,
    SEARCHING_DRIVER,
    DRIVER_HOME,
    DRIVER_RIDE_REQUEST,
    DRIVER_PICKUP,
    DRIVER_DETAILS,
    PASSENGER_VERIFICATION,
    LIVE_TRIP,
    TRIP_SUMMARY,
    RIDER_PAYMENT,
    RECEIPT
}

private const val PREFS_NAME = "surgex_preferences"
private const val LAST_MODE_KEY = "last_mode"

@Composable
fun SurgeXNavigation() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val preferences = remember {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    val authController = remember { AuthController() }
    val tripController = remember { SurgeXTripController() }

    var currentScreen by remember { mutableStateOf(SurgeXScreen.SPLASH) }
    var selectedRole by remember { mutableStateOf(UserRole.RIDER) }
    var checkingSession by remember { mutableStateOf(true) }
    var pendingPhone by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (!authController.isLoggedIn) {
            checkingSession = false
            currentScreen = SurgeXScreen.LOGIN
        } else {
            val profile = authController.getCurrentUser()
            if (profile == null) {
                authController.logout()
                checkingSession = false
                currentScreen = SurgeXScreen.LOGIN
            } else {
                selectedRole = profile.activeMode
                preferences.edit().putString(LAST_MODE_KEY, profile.activeMode.name).apply()
                checkingSession = false
                currentScreen = if (profile.activeMode == UserRole.RIDER)
                    SurgeXScreen.RIDER_HOME else SurgeXScreen.DRIVER_HOME
            }
        }
    }

    if (checkingSession) {
        SplashScreen {}
        return
    }

    when (currentScreen) {

        SurgeXScreen.SPLASH -> {
            SplashScreen { currentScreen = SurgeXScreen.LOGIN }
        }

        SurgeXScreen.LOGIN -> {
            LoginScreen(
                authController = authController,
                onLoginSuccess = {
                    scope.launch {
                        val profile = authController.getCurrentUser()
                        selectedRole = profile?.activeMode ?: UserRole.RIDER
                        currentScreen = if (selectedRole == UserRole.RIDER)
                            SurgeXScreen.RIDER_HOME else SurgeXScreen.DRIVER_HOME
                    }
                },
                onRegister = { currentScreen = SurgeXScreen.REGISTER },
                onBack = { currentScreen = SurgeXScreen.LOGIN }
            )
        }

        SurgeXScreen.REGISTER -> {
            RegisterScreen(
                role = selectedRole,
                authController = authController,
                onRegisterSuccess = { phone ->
                    pendingPhone = phone
                    currentScreen = SurgeXScreen.PHONE_VERIFY
                },
                onBack = { currentScreen = SurgeXScreen.LOGIN }
            )
        }

        SurgeXScreen.PHONE_VERIFY -> {
            PhoneVerifyScreen(
                phoneNumber = pendingPhone,
                authController = authController,
                onCodeSent = { currentScreen = SurgeXScreen.OTP_VERIFY },
                onBack = { currentScreen = SurgeXScreen.REGISTER }
            )
        }

        SurgeXScreen.OTP_VERIFY -> {
            OtpScreen(
                phoneNumber = pendingPhone,
                authController = authController,
                onVerified = {
                    currentScreen = if (selectedRole == UserRole.RIDER)
                        SurgeXScreen.RIDER_HOME else SurgeXScreen.DRIVER_HOME
                },
                onBack = { currentScreen = SurgeXScreen.PHONE_VERIFY }
            )
        }

        SurgeXScreen.RIDER_HOME -> {
            RiderHomeScreen(
                onChooseRide = { currentScreen = SurgeXScreen.RIDE_SELECTION },
                onSwitchToDriver = {
                    scope.launch {
                        val saved = authController.switchMode(UserRole.DRIVER)
                        if (saved is com.surgex.app.auth.AuthResult.Success) {
                            selectedRole = UserRole.DRIVER
                            preferences.edit().putString(LAST_MODE_KEY, UserRole.DRIVER.name).apply()
                            currentScreen = SurgeXScreen.DRIVER_HOME
                        }
                    }
                },
                onBack = { currentScreen = SurgeXScreen.LOGIN }
            )
        }

        SurgeXScreen.RIDE_SELECTION -> {
            RideSelectionScreen(
                onBack = { currentScreen = SurgeXScreen.RIDER_HOME },
                onConfirmRide = {
                    tripController.createRide(
                        riderName = "SurgeX Rider",
                        driverName = "SurgeX Driver",
                        pickupAddress = "Current pickup",
                        destinationAddress = "Cape Town CBD"
                    )
                    currentScreen = SurgeXScreen.SEARCHING_DRIVER
                }
            )
        }

        SurgeXScreen.SEARCHING_DRIVER -> {
            SearchingDriverScreen(
                onDriverFound = { currentScreen = SurgeXScreen.DRIVER_HOME },
                onCancel = {
                    tripController.clear()
                    currentScreen = SurgeXScreen.RIDER_HOME
                }
            )
        }

        SurgeXScreen.DRIVER_HOME -> {
            DriverHomeScreen(
                onOnlineChanged = {},
                onRideRequest = { currentScreen = SurgeXScreen.DRIVER_DETAILS },
                onSwitchToRider = {
                    scope.launch {
                        val saved = authController.switchMode(UserRole.RIDER)
                        if (saved is com.surgex.app.auth.AuthResult.Success) {
                            selectedRole = UserRole.RIDER
                            preferences.edit().putString(LAST_MODE_KEY, UserRole.RIDER.name).apply()
                            currentScreen = SurgeXScreen.RIDER_HOME
                        }
                    }
                },
                onBack = { currentScreen = SurgeXScreen.LOGIN }
            )
        }

        SurgeXScreen.DRIVER_DETAILS -> {
            DriverDetailsScreen(
                authController = authController,
                onSuccess = { currentScreen = SurgeXScreen.DRIVER_HOME },
                onBack = { currentScreen = SurgeXScreen.DRIVER_HOME }
            )
        }

        SurgeXScreen.DRIVER_RIDE_REQUEST -> {
            DriverRideRequestScreen(
                onAccept = { currentScreen = SurgeXScreen.DRIVER_PICKUP },
                onDecline = { currentScreen = SurgeXScreen.DRIVER_HOME }
            )
        }

        SurgeXScreen.DRIVER_PICKUP -> {
            DriverPickupScreen(
                onArrived = { currentScreen = SurgeXScreen.PASSENGER_VERIFICATION },
                onCancel = { currentScreen = SurgeXScreen.DRIVER_HOME }
            )
        }

        SurgeXScreen.PASSENGER_VERIFICATION -> {
            PassengerVerificationScreen(
                onStartTrip = {
                    tripController.startTrip()
                    currentScreen = SurgeXScreen.LIVE_TRIP
                },
                onCancel = { currentScreen = SurgeXScreen.DRIVER_HOME }
            )
        }

        SurgeXScreen.LIVE_TRIP -> {
            LiveTripScreen(
                onEndTrip = {
                    tripController.updateTripMetrics(distanceKm = 12.8, durationMinutes = 24)
                    tripController.completeTrip(paymentMethod = PaymentMethod.CASH)
                    currentScreen = SurgeXScreen.TRIP_SUMMARY
                },
                onSafety = {}
            )
        }

        SurgeXScreen.TRIP_SUMMARY -> {
            val completedTrip = tripController.completedTrip
            if (completedTrip == null) {
                currentScreen = SurgeXScreen.DRIVER_HOME
            } else {
                TripSummaryScreen(
                    fare = completedTrip.fare,
                    distanceKm = completedTrip.ride.distanceKm,
                    durationMinutes = completedTrip.ride.durationMinutes,
                    onDone = { currentScreen = SurgeXScreen.RIDER_PAYMENT }
                )
            }
        }

        SurgeXScreen.RIDER_PAYMENT -> {
            val completedTrip = tripController.completedTrip
            if (completedTrip == null) {
                currentScreen = SurgeXScreen.RIDER_HOME
            } else {
                RiderPaymentScreen(
                    total = completedTrip.fare.total,
                    onPaymentSuccess = { currentScreen = SurgeXScreen.RECEIPT },
                    onCancel = { currentScreen = SurgeXScreen.TRIP_SUMMARY }
                )
            }
        }

        SurgeXScreen.RECEIPT -> {
            val completedTrip = tripController.completedTrip
            if (completedTrip == null) {
                currentScreen = SurgeXScreen.RIDER_HOME
            } else {
                ReceiptScreen(
                    ride = completedTrip.ride,
                    receipt = completedTrip.receipt,
                    fare = completedTrip.fare,
                    paymentMethod = completedTrip.ride.paymentMethod,
                    onDone = {
                        tripController.clear()
                        currentScreen = SurgeXScreen.RIDER_HOME
                    }
                )
            }
        }
    }
}
