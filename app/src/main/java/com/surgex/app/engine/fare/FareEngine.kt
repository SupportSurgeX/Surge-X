package com.surgex.app.engine.fare

import com.surgex.app.domain.fare.FareBreakdown
import com.surgex.app.domain.fare.FareInput

class FareEngine {

    fun calculate(
        distanceKm: Double,
        durationMinutes: Int,
        surgeMultiplier: Double = 1.0,
        baseFare: Double = 20.0,
        perKm: Double = 8.50,
        perMinute: Double = 1.50,
        bookingFee: Double = 5.00,
        waitingFee: Double = 0.00,
        tolls: Double = 0.00
    ): FareBreakdown {

        val normal = FareInput(
            distanceKm = distanceKm,
            durationMinutes = durationMinutes,
            baseFare = baseFare,
            perKm = perKm,
            perMinute = perMinute,
            bookingFee = bookingFee,
            waitingFee = waitingFee,
            tolls = tolls
        )

        val distanceFare = normal.distanceKm * normal.perKm
        val timeFare = normal.durationMinutes * normal.perMinute

        val subtotal =
            normal.baseFare +
            distanceFare +
            timeFare +
            normal.bookingFee +
            normal.waitingFee +
            normal.tolls

        val total = subtotal * surgeMultiplier

        /*
         * Surge is included in the final total.
         * The existing FareBreakdown model does not expose
         * surgeAmount, so we preserve compatibility with the UI.
         */
        return FareBreakdown(
            baseFare = normal.baseFare,
            distanceFare = distanceFare,
            timeFare = timeFare,
            bookingFee = normal.bookingFee,
            waitingFee = normal.waitingFee,
            tolls = normal.tolls,
            total = total
        )
    }
}
