package com.surgex.app.engine.receipt

import com.surgex.app.domain.receipt.Receipt
import java.util.UUID

class ReceiptEngine {

    fun generate(
        rideId: String,
        amount: Double
    ): Receipt {
        return Receipt(
            id = "RCP-${UUID.randomUUID()}",
            rideId = rideId,
            amount = amount
        )
    }
}
