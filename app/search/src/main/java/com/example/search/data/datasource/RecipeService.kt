package com.example.search.data.datasource

import com.example.search.data.GET_RECIPES_URL
import com.example.search.domain.entity.Recipe
import retrofit2.Response
import retrofit2.http.GET

interface RecipeService {
    @GET(GET_RECIPES_URL)
    suspend fun getRecipes(): Response<List<Recipe>>
}