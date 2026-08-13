package com.surgex.app.domain.ride

enum class RideStatus {
    REQUESTED,
    ACCEPTED,
    DRIVER_EN_ROUTE,
    DRIVER_ARRIVED,
    PASSENGER_VERIFIED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}
