package com.surgex.app.engine.trip

data class TripTelemetry(
    val distanceKm: Double = 0.0,
    val durationMinutes: Int = 0,
    val isTracking: Boolean = false
)

class TripTelemetryEngine {

    private var startTimeMillis: Long = 0L
    private var distanceKm: Double = 0.0

    fun start() {
        startTimeMillis = System.currentTimeMillis()
        distanceKm = 0.0
    }

    fun addDistance(distanceDeltaKm: Double) {
        if (distanceDeltaKm > 0) {
            distanceKm += distanceDeltaKm
        }
    }

    fun snapshot(): TripTelemetry {

        val durationMinutes =
            if (startTimeMillis == 0L) {
                0
            } else {
                ((System.currentTimeMillis() - startTimeMillis) / 60_000L)
                    .toInt()
            }

        return TripTelemetry(
            distanceKm = distanceKm,
            durationMinutes = durationMinutes,
            isTracking = startTimeMillis != 0L
        )
    }

    fun stop(): TripTelemetry {
        return snapshot().copy(isTracking = false)
    }

    fun reset() {
        startTimeMillis = 0L
        distanceKm = 0.0
    }
}
