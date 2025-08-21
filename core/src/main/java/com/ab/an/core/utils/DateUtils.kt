package com.ab.an.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    const val DOB_PATTERN = "dd-MM-yyyy"
    const val BIRTH_DAY_PATTERN = "dd MMMM yyyy"
    const val ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    fun formatDateString(dateString: String?, inputFormat: String, outputFormat: String): String {
        if (dateString.isNullOrEmpty()) return ""
        return try {
            val inputFormatter = SimpleDateFormat(inputFormat, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val outputFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
            val date = inputFormatter.parse(dateString)
            if (date != null) outputFormatter.format(date) else ""
        } catch (e: ParseException) {
            ""
        }
    }


    fun convertMillisToDate(millis: Long): String {
        return try {
            val formatter = SimpleDateFormat(DOB_PATTERN, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            return formatter.format(Date(millis))
        } catch (e: Exception) {
            ""
        }
    }

    fun convertDateToMillis(dateStr: String): Long? {
        return try {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = formatter.parse(dateStr)
            date?.time
        } catch (e: Exception) {
            null
        }
    }
}
