package com.surgex.app.domain.verification

data class DriverVerification(
    val driverId: String,

    val status: VerificationStatus =
        VerificationStatus.NOT_STARTED,

    val documents: List<DriverDocument> = emptyList(),

    val faceVerificationRequired: Boolean = true,

    val faceVerified: Boolean = false,

    val livenessVerified: Boolean = false,

    val identityMatch: Boolean = false,

    val reviewedAt: Long? = null,

    val rejectionReason: String? = null
) {

    fun isFullyVerified(): Boolean {
        return status == VerificationStatus.VERIFIED &&
                faceVerified &&
                livenessVerified &&
                identityMatch
    }
}
