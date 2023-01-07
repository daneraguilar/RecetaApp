package com.example.search.data.datasource

import com.example.search.domain.RecipeResultRepository


interface RecipeRemote  {
    suspend fun getRecipes() : RecipeResultRepository
}