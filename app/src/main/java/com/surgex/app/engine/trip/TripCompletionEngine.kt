package com.surgex.app.engine.trip

import com.surgex.app.domain.payment.Payment
import com.surgex.app.domain.payment.PaymentMethod
import com.surgex.app.domain.receipt.Receipt
import com.surgex.app.domain.ride.RideSession
import com.surgex.app.domain.ride.RideStatus
import com.surgex.app.domain.fare.FareBreakdown
import com.surgex.app.engine.earnings.DriverEarnings
import com.surgex.app.engine.earnings.DriverEarningsEngine
import com.surgex.app.engine.fare.FareEngine
import com.surgex.app.engine.payment.PaymentEngine
import com.surgex.app.engine.receipt.ReceiptEngine

data class CompletedTrip(
    val ride: RideSession,
    val fare: FareBreakdown,
    val payment: Payment,
    val receipt: Receipt,
    val driverEarnings: DriverEarnings
)

class TripCompletionEngine(
    private val fareEngine: FareEngine = FareEngine(),
    private val paymentEngine: PaymentEngine = PaymentEngine(),
    private val receiptEngine: ReceiptEngine = ReceiptEngine(),
    private val earningsEngine: DriverEarningsEngine = DriverEarningsEngine()
) {

    fun completeTrip(
        ride: RideSession,
        paymentMethod: PaymentMethod = PaymentMethod.CASH,
        surgeMultiplier: Double = 1.0
    ): CompletedTrip {

        val fare = fareEngine.calculate(
            distanceKm = ride.distanceKm,
            durationMinutes = ride.durationMinutes,
            surgeMultiplier = surgeMultiplier
        )

        ride.baseFare = fare.baseFare
        ride.distanceFare = fare.distanceFare
        ride.timeFare = fare.timeFare
        ride.surgeAmount =
            fare.total -
            (
                fare.baseFare +
                fare.distanceFare +
                fare.timeFare +
                fare.bookingFee +
                fare.waitingFee +
                fare.tolls
            )

        ride.totalFare = fare.total
        ride.status = RideStatus.COMPLETED

        ride.paymentMethod = paymentMethod.name
        ride.paymentStatus = "Paid"

        val payment = paymentEngine.processPayment(
            paymentEngine.createPayment(
                amount = fare.total,
                method = paymentMethod
            )
        )

        val receipt = receiptEngine.generate(
            rideId = ride.rideId,
            amount = fare.total
        )

        val earnings = earningsEngine.calculate(
            fare = fare.total
        )

        return CompletedTrip(
            ride = ride,
            fare = fare,
            payment = payment,
            receipt = receipt,
            driverEarnings = earnings
        )
    }
}
