package com.surgex.app.data.models

import com.google.firebase.Timestamp

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val profilePictureUrl: String = "",
    val role: String = "RIDER", // RIDER or DRIVER
    val activeMode: String = "RIDER",
    val phoneVerified: Boolean = false,
    val accountStatus: String = "ACTIVE",
    val createdAt: Long = System.currentTimeMillis(),
    val lastLogin: Long = System.currentTimeMillis(),
    val driverProfile: DriverProfile? = null,
    val riderProfile: RiderProfile? = null
)

data class DriverProfile(
    val uid: String = "",
    val licenseNumber: String = "",
    val licenseExpiry: String = "",
    val licensePictureUrl: String = "",
    val carBrand: String = "",
    val carModel: String = "",
    val carColor: String = "",
    val carYear: String = "",
    val carCategory: String = "", // Sedan, SUV, Minibus, Truck, Van, Hatchback
    val licensePlate: String = "",
    val carPictureUrls: List<String> = emptyList(),
    val verificationStatus: String = "PENDING", // PENDING, APPROVED, REJECTED
    val dekraStatus: String = "NOT_SUBMITTED",
    val criminalRecordStatus: String = "NOT_SUBMITTED",
    val totalDrivingHours: Long = 0L,
    val lastDrivingSession: Long = 0L,
    val averageRating: Float = 5.0f,
    val totalRides: Int = 0,
    val approvedDate: Long? = null,
    val rejectionReason: String? = null
)

data class RiderProfile(
    val uid: String = "",
    val locationHistory: List<String> = emptyList(),
    val favoriteLocations: Map<String, String> = emptyMap(), // locationName to coordinates
    val averageRating: Float = 5.0f,
    val totalRides: Int = 0,
    val preferredPaymentMethod: String = "CARD",
    val emergencyContacts: List<EmergencyContact> = emptyList()
)

data class EmergencyContact(
    val name: String = "",
    val phone: String = "",
    val relationship: String = ""
)

data class DrivingSession(
    val sessionId: String = "",
    val driverId: String = "",
    val startTime: Long = 0L,
    val endTime: Long? = null,
    val durationMinutes: Long = 0L,
    val distanceKm: Float = 0f,
    val startLocation: String = "",
    val endLocation: String = ""
)
