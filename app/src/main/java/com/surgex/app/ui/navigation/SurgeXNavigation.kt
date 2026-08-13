package com.surgex.app.ui.navigation

import androidx.compose.runtime.*
import com.surgex.app.core.trip.SurgeXTripController
import com.surgex.app.domain.payment.PaymentMethod
import com.surgex.app.ui.screens.driver.*
import com.surgex.app.ui.screens.onboarding.RoleSelectionScreen
import com.surgex.app.ui.screens.rider.*
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

    val tripController = remember {
        SurgeXTripController()
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
                onDriverFound = {
                    currentScreen = SurgeXScreen.DRIVER_HOME
                },
                onCancel = {
                    tripController.clear()
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
                    tripController.startTrip()
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

                    // Temporary trip metrics until GPS is connected.
                    tripController.updateTripMetrics(
                        distanceKm = 12.8,
                        durationMinutes = 24
                    )

                    tripController.completeTrip(
                        paymentMethod = PaymentMethod.CASH
                    )

                    currentScreen = SurgeXScreen.TRIP_SUMMARY
                },
                onSafety = {
                    // Safety engine will be connected here.
                }
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
                    onDone = {
                        currentScreen = SurgeXScreen.RIDER_PAYMENT
                    }
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
                    onPaymentSuccess = {
                        currentScreen = SurgeXScreen.RECEIPT
                    },
                    onCancel = {
                        currentScreen = SurgeXScreen.TRIP_SUMMARY
                    }
                )
            }
        }

        SurgeXScreen.RECEIPT ->
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
