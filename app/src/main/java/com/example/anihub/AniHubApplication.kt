package com.example.anihub

import android.app.Application
import com.example.anihub.di.AppComponent
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.di.modules.ApiModule
import com.example.anihub.di.modules.AppModule
import com.example.anihub.di.modules.CacheModule
import com.example.anihub.di.modules.ContextModule


class AniHubApplication : Application() {

    companion object {
        @JvmStatic lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerAppComponent.builder()
            .cacheModule(CacheModule())
            .apiModule(ApiModule())
            .appModule(AppModule(this))
            .contextModule(ContextModule())
            .build()
        graph.inject(this)
    }
}