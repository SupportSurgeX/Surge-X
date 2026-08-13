package com.surgex.app.domain.ride

data class RideSession(
    val rideId: String,
    val riderName: String,
    val driverName: String,
    val pickupAddress: String,
    val destinationAddress: String,

    val requestedAt: Long = System.currentTimeMillis(),

    var status: RideStatus = RideStatus.REQUESTED,

    var distanceKm: Double = 0.0,
    var durationMinutes: Int = 0,

    var baseFare: Double = 0.0,
    var distanceFare: Double = 0.0,
    var timeFare: Double = 0.0,
    var surgeAmount: Double = 0.0,
    var totalFare: Double = 0.0,

    var paymentMethod: String = "Cash",
    var paymentStatus: String = "Pending"
)
