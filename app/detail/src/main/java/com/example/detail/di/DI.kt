package com.example.detail.di

import com.example.common.BASE_URL
import com.example.common.NetworkManagerState
import com.example.common.NetworkManagerStateImpl
import com.example.detail.data.RecipeDetailRepositoryImpl
import com.example.detail.data.datasource.remote.RecipeDetailRemote
import com.example.detail.data.datasource.remote.RecipeDetailService
import com.example.detail.data.datasource.remote.RecipeDetailRemoteImpl
import com.example.detail.domain.GetRecipeDetailUseCase
import com.example.detail.domain.RecipeDetailRepository
import com.example.detail.ui.DetailViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val DetailModule = module {

    factory<RecipeDetailService> {
        val okHttpClient =
            OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor()).build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeDetailService::class.java)
    }

    single<NetworkManagerState>(override = true) { NetworkManagerStateImpl(androidContext()) }
    single<RecipeDetailRemote> { RecipeDetailRemoteImpl(get()) }
    single<RecipeDetailRepository> { RecipeDetailRepositoryImpl(get(),get()) }

    single { GetRecipeDetailUseCase(get()) }
    viewModel { DetailViewModel(get()) }
}