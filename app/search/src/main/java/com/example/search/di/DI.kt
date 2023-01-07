package com.example.search.di

import com.example.common.BASE_URL
import com.example.common.NetworkManagerState
import com.example.common.NetworkManagerStateImpl
import com.example.search.data.RecipeRepositoryImpl
import com.example.search.data.datasource.RecipeRemote
import com.example.search.data.datasource.RecipeRemoteImpl
import com.example.search.data.datasource.RecipeService
import com.example.search.domain.GetRecipesUseCase
import com.example.search.domain.RecipeRepository
import com.example.search.ui.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val SearchModule = module {

    factory<RecipeService> {
        val okHttpClient =
            OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor()).build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeService::class.java)
    }

    single<NetworkManagerState>(override = true) { NetworkManagerStateImpl(androidContext())}
    single<RecipeRemote> { RecipeRemoteImpl(get()) }
    single<RecipeRepository> { RecipeRepositoryImpl(get(),get()) }

    single { GetRecipesUseCase(get()) }
    viewModel { SearchViewModel(get()) }
}