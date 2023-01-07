package com.example.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultUseCaseError
import com.example.detail.domain.GetRecipeDetailUseCase
import com.example.detail.domain.entity.RecipeDetail
import kotlinx.coroutines.*

class DetailViewModel(
    private val _getRecipesUseCase: GetRecipeDetailUseCase,
    private val _dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private var _job: Job? = null
    private val _uiState = MutableLiveData<DetailUIState>()
    var recipeDetail: RecipeDetail? = null

    val uiState: LiveData<DetailUIState> = _uiState

    fun getRecipe(uuid: String) {
        _job?.cancel()
        _job = viewModelScope.launch(_dispatcher) {

            when (val result = _getRecipesUseCase(uuid)) {

                is Result.Success -> withContext(Dispatchers.Main) {
                    _uiState.value = DetailUIState.Loading(false)
                    result.value?.let {
                        _uiState.value = DetailUIState.HasRecipesContent(it)
                        recipeDetail = it
                    } ?: kotlin.run {
                        _uiState.value = DetailUIState.NotContent
                    }

                }
                is Result.Failure -> {
                    when (result.reason) {
                        is ResultUseCaseError.NetworkError -> withContext(Dispatchers.Main) {
                            _uiState.value = DetailUIState.Loading(false)
                            _uiState.value = DetailUIState.OffLineNetwork
                        }
                        is ResultUseCaseError.UnKnowError -> withContext(Dispatchers.Main) {
                            _uiState.value = DetailUIState.Loading(false)
                            _uiState.value = DetailUIState.Error(DEFAULT_ERROR_MESSAGE)
                        }
                    }

                }
            }
        }

    }


}