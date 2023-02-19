package com.example.search.domin

import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.common.ResultUseCaseError
import com.example.search.SearchTestFactory
import com.example.search.domain.GetRecipesUseCase
import com.example.search.domain.RecipeRepository
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
internal class GetRecipesUseCaseTest : TestCase() {

    @Mock
    private lateinit var _repository: RecipeRepository


    @InjectMocks
    private lateinit var _getRecipesUseCase: GetRecipesUseCase

    @Test
    fun `when get recipes then  return List of 5 recipes`() {
        runBlocking {

            val expect = 5
            val list = SearchTestFactory.getListRecipes()
            Mockito.`when`(_repository.getRecipes()).thenReturn(Result.Success(list))

            when (val result = _getRecipesUseCase()) {

                is Result.Success -> {
                    assertEquals(result.value.size, expect)
                }
                is Result.Failure -> assert(false)
            }

        }
    }

    @Test
    fun `when get recipes with out internet then  return Network error`() {
        runBlocking {

            Mockito.`when`(_repository.getRecipes())
                .thenReturn(Result.Failure(ResultRepositoryError.NetworkError))

            when (val result = _getRecipesUseCase()) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultUseCaseError.NetworkError)
            }

        }
    }

    @Test
    fun `when get recipes with unknow error then  return unKnow error`() {
        runBlocking {

            Mockito.`when`(_repository.getRecipes()).thenReturn(
                Result.Failure(
                    ResultRepositoryError.UnKnowError(
                        DEFAULT_ERROR_MESSAGE
                    )
                )
            )

            when (val result = _getRecipesUseCase()) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultUseCaseError.UnKnowError)
            }

        }
    }

}