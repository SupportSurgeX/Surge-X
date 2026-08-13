package com.surgex.app.domain.receipt

data class Receipt(
    val id: String,
    val rideId: String,
    val amount: Double,
    val issuedAt: Long = System.currentTimeMillis()
)
