package com.quynhlm.dev.furnitureapp.core.validation

class EmailFormat {
    var regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
    fun isEmailFormat(email : String): Boolean {
        // Create a Regex object
        val regex = Regex(regexEmail)

        // Check if the email matches the regex pattern
        return regex.matches(email)
    }
}