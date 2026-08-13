package com.surgex.app.domain.fare

data class FareInput(
    val distanceKm: Double,
    val durationMinutes: Int,
    val baseFare: Double = 20.0,
    val perKm: Double = 8.50,
    val perMinute: Double = 1.50,
    val bookingFee: Double = 5.00,
    val waitingFee: Double = 0.00,
    val tolls: Double = 0.00
)

data class FareBreakdown(
    val baseFare: Double,
    val distanceFare: Double,
    val timeFare: Double,
    val bookingFee: Double,
    val waitingFee: Double,
    val tolls: Double,
    val total: Double
)

object FareCalculator {

    fun calculate(input: FareInput): FareBreakdown {

        val distanceFare =
            input.distanceKm * input.perKm

        val timeFare =
            input.durationMinutes * input.perMinute

        val total =
            input.baseFare +
            distanceFare +
            timeFare +
            input.bookingFee +
            input.waitingFee +
            input.tolls

        return FareBreakdown(
            baseFare = input.baseFare,
            distanceFare = distanceFare,
            timeFare = timeFare,
            bookingFee = input.bookingFee,
            waitingFee = input.waitingFee,
            tolls = input.tolls,
            total = total
        )
    }
}
