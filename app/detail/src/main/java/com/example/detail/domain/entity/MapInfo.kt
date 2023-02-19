package com.example.detail.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapInfo(
    val name: String,
    val location: Location
) : Parcelable