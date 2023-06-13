package com.kudashov.learning_compose.base.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoStatistics(
    val downloads: Int,
    val views: Int
): Parcelable
