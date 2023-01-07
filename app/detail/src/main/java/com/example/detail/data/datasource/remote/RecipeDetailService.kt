package com.example.detail.data.datasource.remote

import com.example.detail.data.GET_RECIPE_DETAIL_URL
import com.example.detail.domain.entity.RecipeDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeDetailService {
    @GET(GET_RECIPE_DETAIL_URL)
    suspend fun getRecipe(@Path("uuid") uuid: String): Response<RecipeDetail>
}