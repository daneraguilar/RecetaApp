package com.example.detail.domain

import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.common.ResultUseCaseError

class GetRecipeDetailUseCase(private val _repository: RecipeDetailRepository) {

    suspend operator fun invoke(uuid: String): RecipeDetailResultUseCase {

        return when (val result = _repository.getRecipe(uuid)) {

            is Result.Success -> Result.Success(result.value)

            is Result.Failure -> when (result.reason) {
                ResultRepositoryError.NetworkError -> Result.Failure(ResultUseCaseError.NetworkError)
                else -> Result.Failure(ResultUseCaseError.UnKnowError(DEFAULT_ERROR_MESSAGE))
            }
        }
    }
}