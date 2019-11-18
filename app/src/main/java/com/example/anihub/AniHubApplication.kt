package com.example.anihub

import android.app.Application
import com.example.anihub.dagger.AppComponent
import com.example.anihub.dagger.DaggerAppComponent
import com.example.anihub.dagger.modules.ApiModule
import com.example.anihub.dagger.modules.AppModule
import com.example.anihub.dagger.modules.ContextModule

class AniHubApplication : Application() {

    private val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .apiModule(ApiModule)
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}