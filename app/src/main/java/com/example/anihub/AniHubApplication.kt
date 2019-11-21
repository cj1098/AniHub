package com.example.anihub

import android.app.Activity
import android.app.Application
import com.example.anihub.di.AppComponent
import com.example.anihub.di.DaggerAppComponent
import com.example.anihub.di.modules.ApiModule
import com.example.anihub.di.modules.AppModule
import com.example.anihub.di.modules.ContextModule
import javax.inject.Inject


class AniHubApplication : Application() {
//
//    @Inject
//    var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity?>? = null
//
//    fun activityInjector(): DispatchingAndroidInjector<Activity?>? {
//        return dispatchingAndroidInjector
//    }

    private val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .contextModule(ContextModule(this))
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}