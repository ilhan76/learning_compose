package com.kudashov.learning_compose.network.i_main.dto

import com.google.gson.annotations.SerializedName
import com.kudashov.learning_compose.domain.PhotoItem
import com.kudashov.learning_compose.domain.Transformable

data class PhotoItemDto(
    @SerializedName("urls") val urls: Urls
) : Transformable<PhotoItem> {

    override fun transform(): PhotoItem = PhotoItem(
        url = urls.regular
    )

}

data class Urls(
    @SerializedName("regular") val regular: String
)