package com.example.search.domain

import com.example.search.domain.entity.Recipe
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.common.ResultUseCaseError

typealias  RecipeResultUseCase = Result<List<Recipe>, ResultUseCaseError>
typealias  RecipeResultRepository = Result<List<Recipe>, ResultRepositoryError>


