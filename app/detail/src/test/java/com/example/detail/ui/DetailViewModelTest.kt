package com.example.detail.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultUseCaseError
import com.example.detail.DetailTestFactory
import com.example.detail.MainCoroutineRule
import com.example.detail.domain.GetRecipeDetailUseCase
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
internal class DetailViewModelTest: TestCase() {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Mock
    private lateinit var _getRecipeDetailUseCase: GetRecipeDetailUseCase

    @Mock
    private lateinit var _observer: Observer<DetailUIState>

    private lateinit var _detailViewModel: DetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        _detailViewModel = DetailViewModel(
            _getRecipeDetailUseCase,
            Dispatchers.Main
        )
    }

    @Test
    fun `when  get recipe with Success response then  change state to has content`() {
        runBlocking {
            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            val recipe = DetailTestFactory.getRecipeDetail()
            Mockito.`when`(_getRecipeDetailUseCase(uuid)).thenReturn(Result.Success(recipe))
            _detailViewModel.uiState.observeForever(_observer)

            _detailViewModel.getRecipe(uuid)

            Mockito.verify(_observer).onChanged(DetailUIState.HasRecipesContent(recipe))

        }
    }

    @Test
    fun `when  get recipe with network exception response then  change state to offline network`() {
        runBlocking {
            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            Mockito.`when`(_getRecipeDetailUseCase(uuid))
                .thenReturn(Result.Failure(ResultUseCaseError.NetworkError))
            _detailViewModel.uiState.observeForever(_observer)

            _detailViewModel.getRecipe(uuid)

            Mockito.verify(_observer).onChanged(DetailUIState.OffLineNetwork)

        }
    }

    @Test
    fun `when  get recipe with un know error response then  change state un know error`() {
        runBlocking {
            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            Mockito.`when`(_getRecipeDetailUseCase(uuid)).thenReturn(
                Result.Failure(
                    ResultUseCaseError.UnKnowError(
                        DEFAULT_ERROR_MESSAGE
                    )
                )
            )
            _detailViewModel.uiState.observeForever(_observer)

            _detailViewModel.getRecipe(uuid)

            Mockito.verify(_observer).onChanged(DetailUIState.Error(DEFAULT_ERROR_MESSAGE))

        }
    }


}