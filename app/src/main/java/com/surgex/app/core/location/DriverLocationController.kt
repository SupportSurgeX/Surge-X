package com.surgex.app.core.location

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.surgex.app.domain.driver.Driver
import com.surgex.app.domain.location.LocationPoint

class DriverLocationController(
    initialDriver: Driver
) {

    var driver by mutableStateOf(initialDriver)
        private set

    fun updateLocation(
        latitude: Double,
        longitude: Double
    ) {

        driver = driver.copy(
            location = LocationPoint(
                latitude = latitude,
                longitude = longitude
            )
        )
    }

    fun setOnline(online: Boolean) {
        driver = driver.copy(
            isOnline = online
        )
    }

    fun clearLocation() {
        driver = driver.copy(
            location = null
        )
    }
}
