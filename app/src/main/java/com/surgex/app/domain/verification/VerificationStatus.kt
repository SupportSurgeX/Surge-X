package com.surgex.app.domain.verification

enum class VerificationStatus {
    NOT_STARTED,
    DOCUMENTS_REQUIRED,
    DOCUMENTS_SUBMITTED,
    FACE_VERIFICATION_REQUIRED,
    UNDER_REVIEW,
    VERIFIED,
    REJECTED,
    EXPIRED,
    SUSPENDED
}
