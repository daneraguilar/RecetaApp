package com.example.detail.domain

import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.common.ResultUseCaseError
import com.example.detail.domain.entity.RecipeDetail

typealias  RecipeDetailResultUseCase = Result<RecipeDetail?, ResultUseCaseError>
typealias  RecipeDetailResultRepository = Result<RecipeDetail?, ResultRepositoryError>