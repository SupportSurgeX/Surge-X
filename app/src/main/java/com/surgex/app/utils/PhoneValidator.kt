package com.surgex.app.utils

object PhoneValidator {
    fun isValidSouthAfricanPhone(phone: String): Boolean {
        val cleaned = phone.replace(" ", "").replace("-", "").replace("(", "").replace(")", "")
        
        return when {
            cleaned.startsWith("+27") && cleaned.length == 12 -> true
            cleaned.startsWith("27") && cleaned.length == 11 -> true
            cleaned.startsWith("0") && cleaned.length == 10 -> true
            else -> false
        }
    }

    fun formatToInternational(phone: String): String {
        val cleaned = phone.replace(" ", "").replace("-", "").replace("(", "").replace(")", "")
        return when {
            cleaned.startsWith("+27") -> cleaned
            cleaned.startsWith("27") -> "+$cleaned"
            cleaned.startsWith("0") -> "+27${cleaned.substring(1)}"
            else -> "+27$cleaned"
        }
    }

    fun formatForDisplay(phone: String): String {
        val cleaned = phone.replace(" ", "").replace("-", "").replace("(", "").replace(")", "")
        return when {
            cleaned.startsWith("+27") -> {
                val number = cleaned.substring(3)
                "+27 ${number.substring(0, 2)} ${number.substring(2, 5)} ${number.substring(5)}"
            }
            cleaned.startsWith("0") -> {
                "+27 ${cleaned.substring(1, 3)} ${cleaned.substring(3, 6)} ${cleaned.substring(6)}"
            }
            else -> phone
        }
    }
}
