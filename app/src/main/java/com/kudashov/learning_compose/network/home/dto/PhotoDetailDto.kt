package com.kudashov.learning_compose.network.home.dto

import com.google.gson.annotations.SerializedName
import com.kudashov.learning_compose.base.domain.PhotoDetail
import com.kudashov.learning_compose.base.domain.util.Transformable

data class PhotoDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("location") val location: LocationDto?
) : Transformable<PhotoDetail> {

    override fun transform(): PhotoDetail = PhotoDetail(
        id = id,
        createdAt = createdAt.orEmpty(),
        description = description,
        url = urls.regular,
        country = location?.country
    )

}

data class LocationDto(
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String
)