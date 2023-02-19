package com.example.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultUseCaseError
import com.example.search.domain.GetRecipesUseCase
import kotlinx.coroutines.*


class SearchViewModel(
    private val _getRecipesUseCase: GetRecipesUseCase,
    private val _dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _job: Job? = null
    private val _uiState = MutableLiveData<SearchUIState>()

    val uiState: LiveData<SearchUIState> = _uiState

    fun getRecipes() {
        _job?.cancel()
        _job = viewModelScope.launch(_dispatcher) {

            when (val result = _getRecipesUseCase()) {

                is Result.Success -> withContext(Dispatchers.Main) {
                    _uiState.value = SearchUIState.Loading(false)
                    if (result.value.isEmpty())
                        _uiState.value = SearchUIState.NotContent
                    else _uiState.value = SearchUIState.HasRecipesContent(result.value)

                }
                is Result.Failure -> {
                    when (result.reason) {
                        is ResultUseCaseError.NetworkError -> withContext(Dispatchers.Main) {
                            _uiState.value = SearchUIState.Loading(false)
                            _uiState.value = SearchUIState.OffLineNetwork
                        }
                        is ResultUseCaseError.UnKnowError -> withContext(Dispatchers.Main) {
                            _uiState.value = SearchUIState.Loading(false)
                            _uiState.value = SearchUIState.Error(DEFAULT_ERROR_MESSAGE)
                        }
                    }

                }
            }
        }

    }

}