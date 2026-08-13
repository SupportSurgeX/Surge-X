package com.surgex.app.domain.driver

data class Driver(
    val id: String,
    val name: String,
    val phone: String,
    val rating: Double = 5.0,
    val isOnline: Boolean = false
)
