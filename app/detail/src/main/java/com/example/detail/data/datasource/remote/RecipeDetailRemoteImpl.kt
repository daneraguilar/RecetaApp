package com.example.detail.data.datasource.remote

import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.HttpCode
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.detail.domain.RecipeDetailResultRepository

class RecipeDetailRemoteImpl (
    private val _recipeService: RecipeDetailService
)
    : RecipeDetailRemote {
    override suspend fun getRecipes(uuid: String): RecipeDetailResultRepository {
        try {
            val result = _recipeService.getRecipe(uuid)

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

                HttpCode.NOT_FOUND.code -> Result.Success(null)

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