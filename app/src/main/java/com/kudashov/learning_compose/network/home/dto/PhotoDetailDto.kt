package com.kudashov.learning_compose.network.home.dto

import com.google.gson.annotations.SerializedName
import com.kudashov.learning_compose.domain.PhotoDetail
import com.kudashov.learning_compose.domain.Transformable

data class PhotoDetailDto(
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String?,
    @SerializedName("urls") val urls: Urls
): Transformable<PhotoDetail> {

    override fun transform(): PhotoDetail = PhotoDetail(
        id = id,
        description = description,
        url = urls.regular
    )

}