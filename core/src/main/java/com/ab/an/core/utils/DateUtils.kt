package com.ab.an.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    private const val CUSTOM_PATTERN = "dd-MM-yyyy"
    private const val ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun fromCustomToIso(dateStr: String?): String {
        if (dateStr.isNullOrEmpty()) return ""
        return try {
            val customFormatter = SimpleDateFormat(CUSTOM_PATTERN, Locale.getDefault())
            val isoFormatter = SimpleDateFormat(ISO_PATTERN, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = customFormatter.parse(dateStr)
            if (date != null) {
                isoFormatter.format(date)
            } else ""
        } catch (e: ParseException) {
            ""
        }
    }

    fun fromIsoToCustom(isoStr: String?): String {
        if (isoStr.isNullOrEmpty()) return ""
        return try {
            val isoFormatter = SimpleDateFormat(ISO_PATTERN, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val customFormatter = SimpleDateFormat(CUSTOM_PATTERN, Locale.getDefault())
            val date = isoFormatter.parse(isoStr)
            if (date != null) customFormatter.format(date) else ""
        } catch (e: ParseException) {
            ""
        }
    }


    fun convertMillisToDate(millis: Long): String {
        return try {
            val formatter = SimpleDateFormat(CUSTOM_PATTERN, Locale.getDefault()).apply {
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
