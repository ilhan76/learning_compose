package com.kudashov.learning_compose.base.domain

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Locale
import kotlin.math.abs

@Parcelize
class PhotoDetail(
    val id: String,
    val createdAt: String,
    val description: String?,
    val url: String,
    val country: String?
): Parcelable {

    val hoursFromCreated: Int get() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val createdAt = simpleDateFormat.parse(createdAt).time
        val currentDate = Calendar.getInstance().time.time
        return (abs(currentDate - createdAt) / (1000 * 60 * 60)).toInt()
    }

    val createdAtFormatted = createdAt.take(10)
}