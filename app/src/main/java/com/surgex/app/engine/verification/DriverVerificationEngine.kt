package com.surgex.app.engine.verification

import com.surgex.app.domain.verification.DriverDocument
import com.surgex.app.domain.verification.DriverVerification
import com.surgex.app.domain.verification.VerificationStatus

class DriverVerificationEngine {

    fun submitDocuments(
        verification: DriverVerification,
        documents: List<DriverDocument>
    ): DriverVerification {

        if (documents.isEmpty()) {
            return verification.copy(
                status = VerificationStatus.DOCUMENTS_REQUIRED
            )
        }

        return verification.copy(
            documents = documents,
            status = VerificationStatus.DOCUMENTS_SUBMITTED
        )
    }

    fun requireFaceVerification(
        verification: DriverVerification
    ): DriverVerification {

        return verification.copy(
            status = VerificationStatus.FACE_VERIFICATION_REQUIRED,
            faceVerificationRequired = true
        )
    }

    fun recordFaceVerification(
        verification: DriverVerification,
        faceVerified: Boolean,
        livenessVerified: Boolean,
        identityMatch: Boolean
    ): DriverVerification {

        if (!faceVerified ||
            !livenessVerified ||
            !identityMatch
        ) {

            return verification.copy(
                status = VerificationStatus.REJECTED,
                faceVerified = faceVerified,
                livenessVerified = livenessVerified,
                identityMatch = identityMatch,
                rejectionReason =
                    "Identity or liveness verification failed"
            )
        }

        return verification.copy(
            status = VerificationStatus.UNDER_REVIEW,
            faceVerified = true,
            livenessVerified = true,
            identityMatch = true
        )
    }

    fun approve(
        verification: DriverVerification
    ): DriverVerification {

        if (!verification.faceVerified ||
            !verification.livenessVerified ||
            !verification.identityMatch
        ) {
            return verification
        }

        return verification.copy(
            status = VerificationStatus.VERIFIED,
            reviewedAt = System.currentTimeMillis(),
            rejectionReason = null
        )
    }

    fun reject(
        verification: DriverVerification,
        reason: String
    ): DriverVerification {

        return verification.copy(
            status = VerificationStatus.REJECTED,
            reviewedAt = System.currentTimeMillis(),
            rejectionReason = reason
        )
    }

    fun suspend(
        verification: DriverVerification,
        reason: String
    ): DriverVerification {

        return verification.copy(
            status = VerificationStatus.SUSPENDED,
            rejectionReason = reason
        )
    }
}
