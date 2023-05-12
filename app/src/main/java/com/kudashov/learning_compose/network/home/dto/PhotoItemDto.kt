package com.kudashov.learning_compose.network.home.dto

import com.google.gson.annotations.SerializedName
import com.kudashov.learning_compose.domain.PhotoItem
import com.kudashov.learning_compose.domain.Transformable

data class PhotoItemDto(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val urls: Urls
) : Transformable<PhotoItem> {

    override fun transform(): PhotoItem = PhotoItem(
        id = id,
        url = urls.regular
    )

}

data class Urls(
    @SerializedName("regular") val regular: String
)