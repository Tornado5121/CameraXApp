package com.zhadko.mycameraapp

import android.app.Application
import com.zhadko.mycameraapp.di.dataModule
import com.zhadko.mycameraapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(dataModule, viewModelModule))
        }
    }

}