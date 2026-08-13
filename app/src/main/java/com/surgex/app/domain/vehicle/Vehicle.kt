package com.surgex.app.domain.vehicle

data class Vehicle(
    val id: String,
    val registration: String,
    val make: String,
    val model: String,
    val color: String,
    val year: Int
)
