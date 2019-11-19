package com.example.anihub

import android.app.Application
import com.example.anihub.di.AppComponent
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.di.modules.AppModule

class AniHubApplication : Application() {

    private val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}