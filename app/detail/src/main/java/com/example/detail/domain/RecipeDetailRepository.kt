package com.example.detail.domain


interface RecipeDetailRepository {
    suspend fun getRecipe(uuid: String) : RecipeDetailResultRepository
}