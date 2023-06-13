package com.kudashov.learning_compose.network.home.dto

import com.google.gson.annotations.SerializedName
import com.kudashov.learning_compose.base.domain.Topic
import com.kudashov.learning_compose.base.domain.util.Transformable

data class TopicDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("featured") val featured: Boolean
): Transformable<Topic> {

    override fun transform() = Topic(
        id = id,
        featured = featured,
        title = title
    )
}