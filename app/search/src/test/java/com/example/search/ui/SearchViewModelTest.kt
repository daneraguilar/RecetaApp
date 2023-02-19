package com.example.search.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultUseCaseError
import com.example.search.MainCoroutineRule
import com.example.search.SearchTestFactory
import com.example.search.domain.GetRecipesUseCase
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
internal class SearchViewModelTest : TestCase() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Mock
    private lateinit var _getRecipeUseCase: GetRecipesUseCase

    @Mock
    private lateinit var _observer: Observer<SearchUIState>

    private lateinit var _searchViewModel: SearchViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        _searchViewModel = SearchViewModel(
            _getRecipeUseCase,
            Dispatchers.Main
        )
    }

    @Test
    fun `when  get recipes with Success response then  change state to has content`() {
        runBlocking {

            val list = SearchTestFactory.getListRecipes()
            Mockito.`when`(_getRecipeUseCase()).thenReturn(Result.Success(list))
            _searchViewModel.uiState.observeForever(_observer)

            _searchViewModel.getRecipes()

            Mockito.verify(_observer).onChanged(SearchUIState.HasRecipesContent(list))

        }
    }

    @Test
    fun `when  get recipes with network exception response then  change state to offline network`() {
        runBlocking {
            Mockito.`when`(_getRecipeUseCase())
                .thenReturn(Result.Failure(ResultUseCaseError.NetworkError))
            _searchViewModel.uiState.observeForever(_observer)

            _searchViewModel.getRecipes()

            Mockito.verify(_observer).onChanged(SearchUIState.OffLineNetwork)

        }
    }

    @Test
    fun `when  get recipes with un know error response then  change state un know error`() {
        runBlocking {
            Mockito.`when`(_getRecipeUseCase()).thenReturn(
                Result.Failure(
                    ResultUseCaseError.UnKnowError(
                        DEFAULT_ERROR_MESSAGE
                    )
                )
            )
            _searchViewModel.uiState.observeForever(_observer)

            _searchViewModel.getRecipes()

            Mockito.verify(_observer).onChanged(SearchUIState.Error(DEFAULT_ERROR_MESSAGE))

        }
    }


}