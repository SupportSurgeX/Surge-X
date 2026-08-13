package com.surgex.app.engine.location

import com.surgex.app.domain.location.LocationPoint
import kotlin.math.*

object DistanceCalculator {

    private const val EARTH_RADIUS_KM = 6371.0

    fun between(
        first: LocationPoint,
        second: LocationPoint
    ): Double {

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

        return EARTH_RADIUS_KM * c
    }
}
