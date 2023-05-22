package com.kudashov.learning_compose.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoItem(
    val id: String,
    val url: String
) : Parcelable