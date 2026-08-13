package com.surgex.app.core.trip

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.surgex.app.domain.fare.FareBreakdown
import com.surgex.app.domain.payment.PaymentMethod
import com.surgex.app.domain.receipt.Receipt
import com.surgex.app.domain.ride.RideSession
import com.surgex.app.engine.earnings.DriverEarnings
import com.surgex.app.engine.trip.CompletedTrip
import com.surgex.app.engine.trip.TripCompletionEngine
import com.surgex.app.engine.trip.TripTelemetry
import com.surgex.app.engine.trip.TripTelemetryEngine
import java.util.UUID

class SurgeXTripController {

    private val telemetryEngine = TripTelemetryEngine()

    var ride by mutableStateOf<RideSession?>(null)
        private set

    var completedTrip by mutableStateOf<CompletedTrip?>(null)
        private set

    var telemetry by mutableStateOf(TripTelemetry())
        private set

    val fare: FareBreakdown?
        get() = completedTrip?.fare

    val receipt: Receipt?
        get() = completedTrip?.receipt

    val driverEarnings: DriverEarnings?
        get() = completedTrip?.driverEarnings

    fun createRide(
        riderName: String,
        driverName: String,
        pickupAddress: String,
        destinationAddress: String
    ) {

        telemetryEngine.reset()

        ride = RideSession(
            rideId = "SX-${UUID.randomUUID().toString().take(8).uppercase()}",
            riderName = riderName,
            driverName = driverName,
            pickupAddress = pickupAddress,
            destinationAddress = destinationAddress
        )

        completedTrip = null
        telemetry = TripTelemetry()
    }

    fun startTrip() {

        telemetryEngine.start()

        telemetry = telemetryEngine.snapshot()

        ride?.status =
            com.surgex.app.domain.ride.RideStatus.IN_PROGRESS
    }

    fun addDistance(distanceDeltaKm: Double) {

        telemetryEngine.addDistance(distanceDeltaKm)

        syncTelemetry()
    }

    fun updateTelemetry() {

        syncTelemetry()
    }

    private fun syncTelemetry() {

        val snapshot = telemetryEngine.snapshot()

        telemetry = snapshot

        ride?.let {
            it.distanceKm = snapshot.distanceKm
            it.durationMinutes = snapshot.durationMinutes
        }
    }

    fun updateTripMetrics(
        distanceKm: Double,
        durationMinutes: Int
    ) {

        ride?.let {
            it.distanceKm = distanceKm
            it.durationMinutes = durationMinutes
        }

        telemetry = TripTelemetry(
            distanceKm = distanceKm,
            durationMinutes = durationMinutes,
            isTracking = telemetry.isTracking
        )
    }

    fun completeTrip(
        paymentMethod: PaymentMethod = PaymentMethod.CASH,
        surgeMultiplier: Double = 1.0
    ) {

        syncTelemetry()

        val currentRide = ride ?: return

        telemetry = telemetryEngine.stop()

        currentRide.distanceKm = telemetry.distanceKm
        currentRide.durationMinutes = telemetry.durationMinutes

        completedTrip =
            TripCompletionEngine().completeTrip(
                ride = currentRide,
                paymentMethod = paymentMethod,
                surgeMultiplier = surgeMultiplier
            )
    }

    fun clear() {

        telemetryEngine.reset()

        ride = null
        completedTrip = null
        telemetry = TripTelemetry()
    }
}
