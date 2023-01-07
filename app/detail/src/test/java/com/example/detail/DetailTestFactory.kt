package com.example.detail

import com.example.detail.domain.entity.Location
import com.example.detail.domain.entity.RecipeDetail
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class DetailTestFactory {

    companion object {

        fun getRecipeDetail() =
            RecipeDetail("c0e7f934-8c44-11ed-a1eb-0242ac120004",
                "Pechugas de pollo rellenas de jamon y queso",
                "Trituramos la galleta. Para ello puedes hacerlo con una batidora americana, una batidora manual o introduciendo las galletas en una bols",
                "image_url",
                3.5f,
                Location(1.0,2.9)
            )

        fun rawResponse () = "{\"key\":[\"somestuff\"]}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    }
}