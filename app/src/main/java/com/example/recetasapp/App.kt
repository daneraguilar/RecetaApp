package com.example.recetasapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.detail.di.DetailModule
import com.example.search.di.SearchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        startKoin {
            androidContext(this@App.baseContext)
            modules(listOf(SearchModule, DetailModule))
        }
    }
}