package com.example.search.data.datasource

import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.HttpCode
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.search.domain.RecipeResultRepository

class RecipeRemoteImpl(
    private val _recipeService: RecipeService
) : RecipeRemote {
    override suspend fun getRecipes(): RecipeResultRepository {
        try {
            val result = _recipeService.getRecipes()

            return when (result.code()) {
                HttpCode.OK.code -> {
                    result.body()?.let { Result.Success(it) } ?: kotlin.run {
                        Result.Failure(
                            ResultRepositoryError.ParserError
                        )
                    }
                }
                HttpCode.BAD_REQUEST.code -> Result.Failure(
                    ResultRepositoryError.BadRequest(
                        result.errorBody()?.string() ?: DEFAULT_ERROR_MESSAGE
                    )
                )
                HttpCode.UN_AUTHORIZED.code -> Result.Failure(ResultRepositoryError.UnAuthorized)
                else -> Result.Failure(ResultRepositoryError.UnKnowError(DEFAULT_ERROR_MESSAGE))
            }
        } catch (e: Exception) {
            return Result.Failure(
                ResultRepositoryError.UnKnowError(
                    e.localizedMessage ?: DEFAULT_ERROR_MESSAGE
                )
            )
        }
    }
}