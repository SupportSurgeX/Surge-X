package com.surgex.app.engine.payment

import com.surgex.app.domain.payment.Payment
import com.surgex.app.domain.payment.PaymentMethod
import com.surgex.app.domain.payment.PaymentStatus
import java.util.UUID

class PaymentEngine {

    fun createPayment(
        amount: Double,
        method: PaymentMethod
    ): Payment {
        return Payment(
            id = UUID.randomUUID().toString(),
            amount = amount,
            method = method,
            status = PaymentStatus.PENDING
        )
    }

    fun processPayment(
        payment: Payment
    ): Payment {
        return payment.copy(
            status = PaymentStatus.PAID
        )
    }
}
