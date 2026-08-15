package com.surgex.app.ui.navigation

import androidx.compose.runtime.*
import com.surgex.app.auth.AuthController
import com.surgex.app.auth.UserRole
import com.surgex.app.core.trip.SurgeXTripController
import com.surgex.app.domain.payment.PaymentMethod
import com.surgex.app.ui.screens.auth.LoginScreen
import com.surgex.app.ui.screens.auth.RegisterScreen
import com.surgex.app.ui.screens.driver.*
import com.surgex.app.ui.screens.onboarding.RoleSelectionScreen
import com.surgex.app.ui.screens.rider.*
import com.surgex.app.ui.screens.splash.SplashScreen

private enum class SurgeXScreen {
    SPLASH,
    ROLE_SELECTION,
    LOGIN,
    REGISTER,
    RIDER_HOME,
    RIDE_SELECTION,
    SEARCHING_DRIVER,
    DRIVER_HOME,
    DRIVER_RIDE_REQUEST,
    DRIVER_PICKUP,
    PASSENGER_VERIFICATION,
    LIVE_TRIP,
    TRIP_SUMMARY,
    RIDER_PAYMENT,
    RECEIPT
}

@Composable
fun SurgeXNavigation() {

    var currentScreen by remember { mutableStateOf(SurgeXScreen.SPLASH) }
    var selectedRole by remember { mutableStateOf(UserRole.RIDER) }

    val tripController = remember { SurgeXTripController() }
    val authController = remember { AuthController() }

    when (currentScreen) {

        SurgeXScreen.SPLASH -> {
            SplashScreen {
                currentScreen = SurgeXScreen.ROLE_SELECTION
            }
        }

        SurgeXScreen.ROLE_SELECTION -> {
            RoleSelectionScreen(
                onRiderSelected = {
                    selectedRole = UserRole.RIDER
                    currentScreen = SurgeXScreen.LOGIN
                },
                onDriverSelected = {
                    selectedRole = UserRole.DRIVER
                    currentScreen = SurgeXScreen.LOGIN
                }
            )
        }

        SurgeXScreen.LOGIN -> {
            LoginScreen(
                role = selectedRole,
                authController = authController,
                onLoginSuccess = {
                    currentScreen = if (selectedRole == UserRole.RIDER)
                        SurgeXScreen.RIDER_HOME else SurgeXScreen.DRIVER_HOME
                },
                onRegister = {
                    currentScreen = SurgeXScreen.REGISTER
                },
                onBack = {
                    currentScreen = SurgeXScreen.ROLE_SELECTION
                }
            )
        }

        SurgeXScreen.REGISTER -> {
            RegisterScreen(
                role = selectedRole,
                authController = authController,
                onRegisterSuccess = {
                    currentScreen = if (selectedRole == UserRole.RIDER)
                        SurgeXScreen.RIDER_HOME else SurgeXScreen.DRIVER_HOME
                },
                onBack = {
                    currentScreen = SurgeXScreen.LOGIN
                }
            )
        }

        SurgeXScreen.RIDER_HOME -> {
            RiderHomeScreen(
                onChooseRide = { currentScreen = SurgeXScreen.RIDE_SELECTION }
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
                onRideRequest = { currentScreen = SurgeXScreen.DRIVER_RIDE_REQUEST }
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
                currentScreen = SurgeXScreen.DRIVER_HOME
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
                currentScreen = SurgeXScreen.DRIVER_HOME
            } else {
                ReceiptScreen(
                    ride = completedTrip.ride,
                    receipt = completedTrip.receipt,
                    fare = completedTrip.fare,
                    paymentMethod = completedTrip.ride.paymentMethod,
                    onDone = {
                        tripController.clear()
                        currentScreen = SurgeXScreen.DRIVER_HOME
                    }
                )
            }
        }
    }
}
