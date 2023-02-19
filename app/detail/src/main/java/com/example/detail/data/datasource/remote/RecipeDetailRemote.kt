package com.example.detail.data.datasource.remote

import com.example.detail.domain.RecipeDetailResultRepository

interface RecipeDetailRemote {
    suspend fun getRecipes(uuid: String): RecipeDetailResultRepository
}