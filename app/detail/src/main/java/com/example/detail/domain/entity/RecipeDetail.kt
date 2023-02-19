package com.example.detail.domain.entity

import com.google.gson.annotations.SerializedName

data class RecipeDetail(
    val uuid: String,
    val name: String,
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val rate: Float,
    val location: Location
)