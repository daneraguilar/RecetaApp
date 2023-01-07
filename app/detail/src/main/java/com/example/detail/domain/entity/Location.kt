package com.example.detail.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location (
    val lat: Double,
    val lng: Double,
) : Parcelable