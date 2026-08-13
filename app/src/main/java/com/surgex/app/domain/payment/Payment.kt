package com.surgex.app.domain.payment

enum class PaymentStatus {
    PENDING,
    PROCESSING,
    PAID,
    FAILED,
    REFUNDED
}

enum class PaymentMethod {
    CASH,
    CARD,
    WALLET
}

data class Payment(
    val id: String,
    val amount: Double,
    val method: PaymentMethod,
    val status: PaymentStatus = PaymentStatus.PENDING
)
