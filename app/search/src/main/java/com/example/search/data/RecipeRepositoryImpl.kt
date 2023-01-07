package com.example.search.data

import com.example.common.NetworkManagerState
import com.example.search.data.datasource.RecipeRemote
import com.example.search.domain.RecipeRepository
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.search.domain.RecipeResultRepository

class RecipeRepositoryImpl (
    private val _recipeService: RecipeRemote,
    private val _networkManagerState: NetworkManagerState,
        ): RecipeRepository {
    override suspend fun getRecipes(): RecipeResultRepository {

        return when {
            _networkManagerState.isConnected() -> _recipeService.getRecipes()
            else -> Result.Failure(ResultRepositoryError.NetworkError)
        }
    }
}