package com.surgex.app.ui.navigation

import androidx.compose.runtime.*
import com.surgex.app.ui.screens.onboarding.RoleSelectionScreen
import com.surgex.app.ui.screens.rider.RideSelectionScreen
import com.surgex.app.ui.screens.rider.RiderHomeScreen
import com.surgex.app.ui.screens.rider.SearchingDriverScreen
import com.surgex.app.ui.screens.rider.RiderPaymentScreen
import com.surgex.app.ui.screens.rider.ReceiptScreen
import com.surgex.app.ui.screens.driver.DriverHomeScreen
import com.surgex.app.ui.screens.driver.DriverRideRequestScreen
import com.surgex.app.ui.screens.driver.DriverPickupScreen
import com.surgex.app.ui.screens.driver.PassengerVerificationScreen
import com.surgex.app.ui.screens.driver.LiveTripScreen
import com.surgex.app.ui.screens.driver.TripSummaryScreen
import com.surgex.app.domain.fare.FareCalculator
import com.surgex.app.domain.fare.FareInput
import com.surgex.app.ui.screens.splash.SplashScreen

private enum class SurgeXScreen {
    SPLASH,
    ROLE_SELECTION,
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

    var currentScreen by remember {
        mutableStateOf(SurgeXScreen.SPLASH)
    }

    when (currentScreen) {

        SurgeXScreen.SPLASH -> {

            SplashScreen {
                currentScreen = SurgeXScreen.ROLE_SELECTION
            }
        }

        SurgeXScreen.ROLE_SELECTION -> {

            RoleSelectionScreen(
                onRiderSelected = {
                    currentScreen = SurgeXScreen.RIDER_HOME
                },
                onDriverSelected = {
                    currentScreen = SurgeXScreen.DRIVER_HOME
                }
            )
        }

        SurgeXScreen.RIDER_HOME -> {

            RiderHomeScreen(
                onChooseRide = {
                    currentScreen = SurgeXScreen.RIDE_SELECTION
                }
            )
        }

        SurgeXScreen.RIDE_SELECTION -> {

            RideSelectionScreen(
                onBack = {
                    currentScreen = SurgeXScreen.RIDER_HOME
                },
                onConfirmRide = {
                    currentScreen = SurgeXScreen.SEARCHING_DRIVER
                }
            )
        }

        SurgeXScreen.SEARCHING_DRIVER -> {

            SearchingDriverScreen(
                onDriverFound = {
                    // Live trip will be connected here.
                },
                onCancel = {
                    currentScreen = SurgeXScreen.RIDER_HOME
                }
            )
        }

        SurgeXScreen.DRIVER_HOME -> {

            DriverHomeScreen(
                onRideRequest = {
                    currentScreen = SurgeXScreen.DRIVER_RIDE_REQUEST
                }
            )
        }

        SurgeXScreen.DRIVER_RIDE_REQUEST -> {

            DriverRideRequestScreen(
                onAccept = {
                    currentScreen = SurgeXScreen.DRIVER_PICKUP
                },
                onDecline = {
                    currentScreen = SurgeXScreen.DRIVER_HOME
                }
            )
        }

        SurgeXScreen.DRIVER_PICKUP -> {

            DriverPickupScreen(
                onArrived = {
                    currentScreen = SurgeXScreen.PASSENGER_VERIFICATION
                },
                onCancel = {
                    currentScreen = SurgeXScreen.DRIVER_HOME
                }
            )
        }

        SurgeXScreen.PASSENGER_VERIFICATION -> {

            PassengerVerificationScreen(
                onStartTrip = {
                    currentScreen = SurgeXScreen.LIVE_TRIP
                },
                onCancel = {
                    currentScreen = SurgeXScreen.DRIVER_HOME
                }
            )
        }

        SurgeXScreen.LIVE_TRIP -> {

            LiveTripScreen(
                onEndTrip = {
                    currentScreen = SurgeXScreen.TRIP_SUMMARY
                },
                onSafety = {
                    // Safety system will be connected here.
                }
            )
        }

        SurgeXScreen.TRIP_SUMMARY -> {

            val fare = FareCalculator.calculate(
                FareInput(
                    distanceKm = 12.8,
                    durationMinutes = 24
                )
            )

            TripSummaryScreen(
                fare = fare,
                distanceKm = 12.8,
                durationMinutes = 24,
                onDone = {
                    currentScreen = SurgeXScreen.RIDER_PAYMENT
                }
            )
        }

        SurgeXScreen.RIDER_PAYMENT -> {

            val fare = FareCalculator.calculate(
                FareInput(
                    distanceKm = 12.8,
                    durationMinutes = 24
                )
            )

            RiderPaymentScreen(
                total = fare.total,
                onPaymentSuccess = {
                    currentScreen = SurgeXScreen.RECEIPT
                },
                onCancel = {
                    currentScreen = SurgeXScreen.TRIP_SUMMARY
                }
            )
        }

        SurgeXScreen.RECEIPT -> {

            val fare = FareCalculator.calculate(
                FareInput(
                    distanceKm = 12.8,
                    durationMinutes = 24
                )
            )

            ReceiptScreen(
                fare = fare,
                distanceKm = 12.8,
                durationMinutes = 24,
                paymentMethod = "Cash",
                onDone = {
                    currentScreen = SurgeXScreen.DRIVER_HOME
                }
            )
        }
    }
}



