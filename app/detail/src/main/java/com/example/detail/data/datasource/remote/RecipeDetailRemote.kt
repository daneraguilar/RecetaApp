package com.example.detail.data.datasource.remote

import com.example.detail.domain.RecipeDetailResultRepository
import com.example.detail.domain.entity.RecipeDetail

interface RecipeDetailRemote {
    suspend fun getRecipes(uuid: String) : RecipeDetailResultRepository
}