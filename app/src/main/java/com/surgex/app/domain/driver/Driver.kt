package com.surgex.app.domain.driver

import com.surgex.app.domain.location.LocationPoint
import com.surgex.app.domain.verification.DriverVerification
import com.surgex.app.domain.verification.VerificationStatus

data class Driver(
    val id: String,
    val name: String,
    val phone: String,

    val rating: Double = 5.0,

    val isOnline: Boolean = false,

    val location: LocationPoint? = null,

    val verification: DriverVerification =
        DriverVerification(driverId = id)
) {

    val isVerified: Boolean
        get() = verification.status == VerificationStatus.VERIFIED

    val canAcceptTrips: Boolean
        get() = isOnline &&
                isVerified &&
                location != null
}
