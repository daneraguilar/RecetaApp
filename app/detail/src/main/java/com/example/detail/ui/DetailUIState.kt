package com.example.detail.ui

import com.example.detail.domain.entity.RecipeDetail

sealed class DetailUIState {
    data class Loading(val visible: Boolean) : DetailUIState()
    data class HasRecipesContent(val recipeDetail: RecipeDetail) : DetailUIState()
    object NotContent : DetailUIState()
    data class Error(val message: String) : DetailUIState()
    object OffLineNetwork : DetailUIState()
}