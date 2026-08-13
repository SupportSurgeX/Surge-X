package com.surgex.app.engine.location

import kotlin.math.*

data class GeoPoint(
    val latitude: Double,
    val longitude: Double
)

class LocationTracker {

    private var lastPoint: GeoPoint? = null
    private var totalDistanceKm = 0.0

    fun updateLocation(
        latitude: Double,
        longitude: Double
    ): Double {

        val current = GeoPoint(latitude, longitude)

        val previous = lastPoint

        if (previous != null) {
            val distance = distanceBetween(previous, current)

            // Ignore tiny GPS noise.
            if (distance >= 0.005) {
                totalDistanceKm += distance
            }
        }

        lastPoint = current

        return totalDistanceKm
    }

    fun currentDistanceKm(): Double {
        return totalDistanceKm
    }

    fun reset() {
        lastPoint = null
        totalDistanceKm = 0.0
    }

    private fun distanceBetween(
        first: GeoPoint,
        second: GeoPoint
    ): Double {

        val earthRadiusKm = 6371.0

        val lat1 = Math.toRadians(first.latitude)
        val lat2 = Math.toRadians(second.latitude)

        val deltaLat =
            Math.toRadians(second.latitude - first.latitude)

        val deltaLon =
            Math.toRadians(second.longitude - first.longitude)

        val a =
            sin(deltaLat / 2).pow(2) +
            cos(lat1) *
            cos(lat2) *
            sin(deltaLon / 2).pow(2)

        val c =
            2 * atan2(
                sqrt(a),
                sqrt(1 - a)
            )

        return earthRadiusKm * c
    }
}
