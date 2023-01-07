package com.example.detail.data

import com.example.common.Result
import com.example.common.ResultRepositoryError
import com.example.detail.DetailTestFactory
import com.example.detail.data.datasource.remote.RecipeDetailRemoteImpl
import com.example.detail.data.datasource.remote.RecipeDetailService
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
internal class RecipeRemoteImplTest: TestCase() {

    @Mock
    private lateinit var _recipeDetailService: RecipeDetailService

    @InjectMocks
    private lateinit var _recipeDetailRemote: RecipeDetailRemoteImpl

    @Test
    fun `when get recipe with success then return recipe`() {
        runBlocking {

            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            val recipe = DetailTestFactory.getRecipeDetail()
            Mockito.`when`(_recipeDetailService.getRecipe(uuid)).thenReturn(Response.success(recipe))

            when (val result =_recipeDetailRemote.getRecipes(uuid)) {

                is Result.Success -> {
                    assertEquals(result.value?.uuid, uuid)
                }
                is Result.Failure -> assert(false)
            }

        }
    }
    @Test
    fun `when get recipes  with error un authorized return default message`() {
        runBlocking {
            val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
            Mockito.`when`(_recipeDetailService.getRecipe(uuid)).thenReturn(Response.error(401,DetailTestFactory.rawResponse()))

            when (val result =_recipeDetailRemote.getRecipes(uuid)) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultRepositoryError.UnAuthorized)
            }

        }
    }
    @Test
    fun `when get recipes  with error un know return un know error`() {
        val uuid = "c0e7f934-8c44-11ed-a1eb-0242ac120004"
        runBlocking {
            Mockito.`when`(_recipeDetailService.getRecipe(uuid)).thenReturn(Response.error(500,DetailTestFactory.rawResponse()))

            when (val result =_recipeDetailRemote.getRecipes(uuid)) {

                is Result.Success -> {
                    assert(false)
                }
                is Result.Failure -> assert(result.reason is ResultRepositoryError.UnKnowError)
            }

        }
    }
}