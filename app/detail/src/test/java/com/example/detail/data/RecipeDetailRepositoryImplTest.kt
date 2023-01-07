package com.example.detail.data

import com.example.common.NetworkManagerState
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.detail.DetailTestFactory
import com.example.detail.data.datasource.remote.RecipeDetailRemote
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipeDetailRepositoryImplTest: TestCase() {

    @Mock
    private lateinit var _recipeDetailRemote: RecipeDetailRemote

    @Mock
    private lateinit var _networkManagerState: NetworkManagerState

    @InjectMocks
    private lateinit var _recipeDetailRepository: RecipeDetailRepositoryImpl

    @Test
    fun `when get recipes  with network online then  return List of 5 recipes`() {
        runBlocking {

            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            val recipe = DetailTestFactory.getRecipeDetail()
            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(true)
            Mockito.`when`(_recipeDetailRemote.getRecipes(uuid)).thenReturn(Result.Success(recipe))

            when (val result = _recipeDetailRepository.getRecipe(uuid)) {

                is Result.Success -> {
                    assertEquals(result.value?.uuid, uuid)
                }
                is Result.Failure -> assert(false)
            }

        }
    }

    @Test
    fun `when get recipes with network offline then  return NetworkError`() {
        runBlocking {
            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(false)

            when (val result = _recipeDetailRepository.getRecipe(uuid)) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultRepositoryError.NetworkError)
            }

        }
    }

}