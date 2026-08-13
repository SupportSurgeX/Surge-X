package com.surgex.app.domain.verification

data class DriverDocument(
    val documentId: String,
    val type: DocumentType,
    val documentNumber: String? = null,
    val issuedAt: Long? = null,
    val expiresAt: Long? = null,
    val submittedAt: Long? = null,
    val verifiedAt: Long? = null,
    val status: VerificationStatus = VerificationStatus.DOCUMENTS_REQUIRED,
    val rejectionReason: String? = null
)
