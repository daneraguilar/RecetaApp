package com.example.search.domain


interface RecipeRepository {
    suspend fun getRecipes() : RecipeResultRepository
}