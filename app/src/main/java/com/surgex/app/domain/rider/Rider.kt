package com.surgex.app.domain.rider

data class Rider(
    val id: String,
    val name: String,
    val phone: String,
    val rating: Double = 5.0
)
