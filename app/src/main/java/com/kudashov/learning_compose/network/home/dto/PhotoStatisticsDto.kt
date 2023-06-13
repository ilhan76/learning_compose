package com.kudashov.learning_compose.network.home.dto

import com.google.gson.annotations.SerializedName
import com.kudashov.learning_compose.base.domain.PhotoStatistics
import com.kudashov.learning_compose.base.domain.util.Transformable

data class PhotoStatisticsDto(
    @SerializedName("downloads") val downloads: StatisticsEntryDto,
    @SerializedName("views") val views: StatisticsEntryDto,
): Transformable<PhotoStatistics> {

    override fun transform(): PhotoStatistics = PhotoStatistics(
        downloads = downloads.total,
        views = views.total
    )
}

data class StatisticsEntryDto(
    @SerializedName("total") val total: Int
)