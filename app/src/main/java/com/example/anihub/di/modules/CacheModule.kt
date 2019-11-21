package com.example.anihub.di.modules

import android.content.Context
import com.example.anihub.CacheFactory
import dagger.Module
import dagger.Provides

@Module (includes = [ContextModule::class])
class CacheModule {

    @Provides
    fun providesCacheFactory(context: Context): CacheFactory {
        return CacheFactory(context)
    }
}