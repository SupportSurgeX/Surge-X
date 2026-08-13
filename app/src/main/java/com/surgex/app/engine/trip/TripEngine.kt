package com.surgex.app.engine.trip

import com.surgex.app.domain.location.LocationPoint
import com.surgex.app.domain.ride.RideSession
import com.surgex.app.domain.ride.RideStatus
import kotlin.math.*

class TripEngine {

    fun startTrip(ride: RideSession): RideSession {
        ride.status = RideStatus.IN_PROGRESS
        return ride
    }

    fun updateTrip(
        ride: RideSession,
        previousLocation: LocationPoint,
        currentLocation: LocationPoint
    ): RideSession {

        val distance = calculateDistanceKm(
            previousLocation.latitude,
            previousLocation.longitude,
            currentLocation.latitude,
            currentLocation.longitude
        )

        ride.distanceKm += distance
        ride.durationMinutes =
            ((currentLocation.timestamp - ride.requestedAt) / 60_000L).toInt()

        return ride
    }

    fun finishTrip(
        ride: RideSession
    ): RideSession {
        ride.status = RideStatus.COMPLETED
        return ride
    }

    private fun calculateDistanceKm(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {

        val earthRadius = 6371.0

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a =
            sin(dLat / 2).pow(2) +
            cos(Math.toRadians(lat1)) *
            cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }
}
