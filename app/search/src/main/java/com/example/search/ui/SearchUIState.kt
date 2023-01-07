package com.example.search.ui

import com.example.search.domain.entity.Recipe

sealed class SearchUIState {
    data class Loading(val visible: Boolean) : SearchUIState()
    data class HasRecipesContent(val list: List<Recipe>) : SearchUIState()
    object NotContent : SearchUIState()
    data class Error(val message: String) : SearchUIState()
    object OffLineNetwork : SearchUIState()
}
