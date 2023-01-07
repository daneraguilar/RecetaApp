package com.example.search.data

import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.search.SearchTestFactory
import com.example.search.data.datasource.RecipeRemoteImpl
import com.example.search.data.datasource.RecipeService
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
internal class RecipeRemoteImplTest : TestCase() {

    @Mock
    private lateinit var _recipeService: RecipeService

    @InjectMocks
    private lateinit var _recipeRemote: RecipeRemoteImpl

    @Test
    fun `when get recipes  then  return List of 5 recipes`() {
        runBlocking {

            val expect = 5
            val list = SearchTestFactory.getListRecipes()
            Mockito.`when`(_recipeService.getRecipes()).thenReturn(Response.success(list))

            when (val result =_recipeRemote.getRecipes()) {

                is Result.Success -> {
                    assertEquals(result.value.size, expect)
                }
                is Result.Failure -> assert(false)
            }

        }
    }
    @Test
    fun `when get recipes  with error un authorized return default message`() {
        runBlocking {
            Mockito.`when`(_recipeService.getRecipes()).thenReturn(Response.error(401,SearchTestFactory.rawResponse()))

            when (val result =_recipeRemote.getRecipes()) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultRepositoryError.UnAuthorized)
            }

        }
    }
    @Test
    fun `when get recipes  with error un know return un know error`() {
        runBlocking {
            Mockito.`when`(_recipeService.getRecipes()).thenReturn(Response.error(500,SearchTestFactory.rawResponse()))

            when (val result =_recipeRemote.getRecipes()) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultRepositoryError.UnKnowError)
            }

        }
    }


}