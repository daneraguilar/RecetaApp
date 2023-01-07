package com.example.search.domain.entity

import com.google.gson.annotations.SerializedName

data class Recipe(
    val uuid: String,
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
)
