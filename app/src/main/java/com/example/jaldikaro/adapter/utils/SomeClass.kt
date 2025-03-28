package com.example.jaldikaro.utils

import java.text.SimpleDateFormat
import java.util.*

object SomeClass {

    // Format Date to String
    fun formatDate(date: Date?): String {
        return if (date != null) {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.format(date)
        } else {
            "No Date Available"
        }
    }

    // Parse String to Date
    fun parseDate(dateString: String): Date? {
        return try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            null
        }
    }

    // Get Current Date as String
    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    // Example Utility Function to Capitalize String
    fun capitalizeString(input: String): String {
        return input.replaceFirstChar { it.uppercaseChar() }
    }
}
