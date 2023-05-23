package com.kudashov.learning_compose.base.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoItem(
    val id: String,
    val url: String
) : Parcelable