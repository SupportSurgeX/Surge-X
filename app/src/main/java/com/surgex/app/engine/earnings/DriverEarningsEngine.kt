package com.surgex.app.engine.earnings

data class DriverEarnings(
    val grossFare: Double,
    val platformFee: Double,
    val driverEarnings: Double
)

class DriverEarningsEngine(
    private val platformCommission: Double = 0.20
) {

    fun calculate(
        fare: Double
    ): DriverEarnings {

        val platformFee = fare * platformCommission
        val driverAmount = fare - platformFee

        return DriverEarnings(
            grossFare = fare,
            platformFee = platformFee,
            driverEarnings = driverAmount
        )
    }
}
