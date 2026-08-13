package com.surgex.app.engine.matching

import com.surgex.app.domain.driver.Driver
import com.surgex.app.domain.location.LocationPoint
import com.surgex.app.engine.location.DistanceCalculator

class MatchingEngine {

    fun findBestDriver(
        drivers: List<Driver>,
        riderLocation: LocationPoint,
        maxDistanceKm: Double = 10.0
    ): Driver? {

        return drivers
            .filter { driver ->
                driver.canAcceptTrips
            }
            .mapNotNull { driver ->

                val driverLocation = driver.location
                    ?: return@mapNotNull null

                val distanceKm =
                    DistanceCalculator.between(
                        riderLocation,
                        driverLocation
                    )

                if (distanceKm <= maxDistanceKm) {
                    DriverCandidate(
                        driver = driver,
                        distanceKm = distanceKm
                    )
                } else {
                    null
                }
            }
            .sortedWith(
                compareBy<DriverCandidate> {
                    it.distanceKm
                }.thenByDescending {
                    it.driver.rating
                }
            )
            .firstOrNull()
            ?.driver
    }

    private data class DriverCandidate(
        val driver: Driver,
        val distanceKm: Double
    )
}
