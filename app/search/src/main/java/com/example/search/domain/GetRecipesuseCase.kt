package com.example.search.domain

import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.common.ResultUseCaseError

class GetRecipesUseCase(private val _repository: RecipeRepository) {

    suspend operator fun invoke(): RecipeResultUseCase {

        return when (val result = _repository.getRecipes()) {

            is Result.Success -> Result.Success(result.value)

            is Result.Failure -> when (result.reason) {
                ResultRepositoryError.NetworkError -> Result.Failure(ResultUseCaseError.NetworkError)
                else -> Result.Failure(ResultUseCaseError.UnKnowError(DEFAULT_ERROR_MESSAGE))
            }
        }
    }
}
