package com.kudashov.learning_compose.base.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoDetail(
    val id: String,
    val description: String?,
    val url: String
): Parcelable