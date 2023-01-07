package com.example.search

import com.example.search.domain.entity.Recipe
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class SearchTestFactory {

    companion object {

        fun getListRecipes() = listOf(
            Recipe("1","nam1","url"),
            Recipe("2","nam2","url"),
            Recipe("3","nam3","url"),
            Recipe("4","nam4","url"),
            Recipe("5","nam5","url")
        )

        fun rawResponse () = "{\"key\":[\"somestuff\"]}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    }

}
