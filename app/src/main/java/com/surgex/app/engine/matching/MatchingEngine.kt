package com.surgex.app.engine.matching

import com.surgex.app.domain.driver.Driver

class MatchingEngine {

    fun findBestDriver(
        drivers: List<Driver>,
        riderLatitude: Double,
        riderLongitude: Double
    ): Driver? {
        return drivers
            .filter { it.isOnline }
            .maxByOrNull { it.rating }
    }
}
