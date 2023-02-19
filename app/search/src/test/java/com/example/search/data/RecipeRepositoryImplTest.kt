package com.example.search.data

import com.example.common.NetworkManagerState
import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.search.SearchTestFactory
import com.example.search.data.datasource.RecipeRemote
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class RecipeRepositoryImplTest : TestCase() {


    @Mock
    private lateinit var _recipeRemote: RecipeRemote

    @Mock
    private lateinit var _networkManagerState: NetworkManagerState

    @InjectMocks
    private lateinit var _recipeRepository: RecipeRepositoryImpl


    @Test
    fun `when get recipes  with network online then  return List of 5 recipes`() {
        runBlocking {

            val expect = 5
            val list = SearchTestFactory.getListRecipes()
            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(true)
            Mockito.`when`(_recipeRemote.getRecipes()).thenReturn(Result.Success(list))

            when (val result = _recipeRepository.getRecipes()) {

                is Result.Success -> {
                    assertEquals(result.value.size, expect)
                }
                is Result.Failure -> assert(false)
            }

        }
    }

    @Test
    fun `when get recipes with network offline then  return NetworkError`() {
        runBlocking {

            Mockito.`when`(_networkManagerState.isConnected()).thenReturn(false)

            when (val result = _recipeRepository.getRecipes()) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultRepositoryError.NetworkError)
            }

        }
    }


}