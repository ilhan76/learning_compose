package com.kudashov.learning_compose.network

object BaseUrls {

    const val BASE_URL = "https://api.unsplash.com/"

    const val LIST_PHOTOS = "/photos"
    const val RANDOM_PHOTO = "$LIST_PHOTOS/random"
    const val TOPICS = "/topics"
    const val PHOTOS_BY_TOPIC = "$TOPICS/{id}/photos"
}