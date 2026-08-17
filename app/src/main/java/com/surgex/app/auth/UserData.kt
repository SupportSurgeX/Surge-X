package com.surgex.app.auth

data class DriverProfile(
    val uid: String = "",
    val licenseNumber: String = "",
    val licenseExpiry: String = "",
    val carBrand: String = "",
    val carModel: String = "",
    val carColor: String = "",
    val carYear: String = "",
    val carCategory: String = "",
    val licensePlate: String = "",
    val verified: Boolean = false,
    val verificationStatus: String = "PENDING",
    val dekraStatus: String = "NOT_SUBMITTED",
    val criminalRecordStatus: String = "NOT_SUBMITTED"
)

data class UserProfileExtended(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val profilePictureUrl: String = "",
    val role: String = "RIDER",
    val activeMode: String = "RIDER",
    val accountStatus: String = "ACTIVE",
    val phoneVerified: Boolean = false,
    val driverProfile: DriverProfile? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLogin: Long = System.currentTimeMillis()
)
