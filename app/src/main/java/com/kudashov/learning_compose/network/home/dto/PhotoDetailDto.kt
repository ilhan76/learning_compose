package com.kudashov.learning_compose.network.home.dto

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import com.google.gson.annotations.SerializedName
import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.util.Transformable
import java.util.Locale

data class PhotoDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("location") val location: LocationDto?
) : Transformable<PhotoDetail> {

    override fun transform(): PhotoDetail = PhotoDetail(
        id = id,
        howManyHoursAgo = calculateTimeDifference(),
        description = description,
        url = urls.regular,
        country = location?.country ?: ""
    )

    private fun calculateTimeDifference(): Int {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val date = simpleDateFormat.parse(createdAt).time
        val currentDate = Calendar.getInstance().time.time
        return (currentDate - date).toInt() / (1000 * 60 * 60)
    }

}

data class LocationDto(
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String
)