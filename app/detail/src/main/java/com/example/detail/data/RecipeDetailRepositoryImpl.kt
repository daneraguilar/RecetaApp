package com.example.detail.data

import com.example.common.*
import com.example.detail.data.datasource.remote.RecipeDetailRemote
import com.example.detail.domain.RecipeDetailRepository
import com.example.detail.domain.RecipeDetailResultRepository

class RecipeDetailRepositoryImpl (
    private val _recipeService: RecipeDetailRemote,
    private val _networkManagerState: NetworkManagerState,
): RecipeDetailRepository {
    override suspend fun getRecipe(uuid: String): RecipeDetailResultRepository {
        return when {
            _networkManagerState.isConnected() -> _recipeService.getRecipes(uuid)
            else -> Result.Failure(ResultRepositoryError.NetworkError)
        }
    }
}