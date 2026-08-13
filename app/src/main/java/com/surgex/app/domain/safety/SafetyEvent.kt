package com.surgex.app.domain.safety

enum class SafetyEventType {
    SOS,
    TRIP_DEVIATION,
    LONG_STOP,
    CRASH_DETECTED,
    DRIVER_CANCELLED,
    RIDER_CANCELLED
}

data class SafetyEvent(
    val id: String,
    val rideId: String,
    val type: SafetyEventType,
    val timestamp: Long = System.currentTimeMillis(),
    val description: String = ""
)
