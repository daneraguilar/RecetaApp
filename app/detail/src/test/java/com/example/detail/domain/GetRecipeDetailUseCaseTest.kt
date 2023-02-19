package com.example.detail.domain

import com.example.common.DEFAULT_ERROR_MESSAGE
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.common.ResultUseCaseError
import com.example.detail.DetailTestFactory
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetRecipeDetailUseCaseTest : TestCase() {

    @Mock
    private lateinit var _repositoryDetail: RecipeDetailRepository


    @InjectMocks
    private lateinit var _getRecipeDetailUseCase: GetRecipeDetailUseCase

    @Test
    fun `when get recipe then  return recipe`() {
        runBlocking {

            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            val recipe = DetailTestFactory.getRecipeDetail()
            Mockito.`when`(_repositoryDetail.getRecipe(uuid)).thenReturn(Result.Success(recipe))

            when (val result = _getRecipeDetailUseCase(uuid)) {

                is Result.Success -> {
                    assertEquals(result.value?.uuid, uuid)
                }
                is Result.Failure -> assert(false)
            }

        }
    }

    @Test
    fun `when get recipe with out internet then  return Network error`() {
        runBlocking {
            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            Mockito.`when`(_repositoryDetail.getRecipe(uuid))
                .thenReturn(Result.Failure(ResultRepositoryError.NetworkError))

            when (val result = _getRecipeDetailUseCase(uuid)) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultUseCaseError.NetworkError)
            }

        }
    }

    @Test
    fun `when get recipe with unknow error then  return unKnow error`() {
        runBlocking {

            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            Mockito.`when`(_repositoryDetail.getRecipe(uuid)).thenReturn(
                Result.Failure(
                    ResultRepositoryError.UnKnowError(
                        DEFAULT_ERROR_MESSAGE
                    )
                )
            )

            when (val result = _getRecipeDetailUseCase(uuid)) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultUseCaseError.UnKnowError)
            }

        }
    }
}