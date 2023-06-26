package com.kudashov.learning_compose.base.util

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale

object DateUtil {

    private const val DATE_ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    private fun calculateTimeDifference(date: String): Int {
        val simpleDateFormat = SimpleDateFormat(DATE_ISO_8601_PATTERN, Locale.US)
        val date = simpleDateFormat.parse(date).time
        val currentDate = Calendar.getInstance().time.time
        return (currentDate - date).toInt() / (1000 * 60 * 60)
    }
}