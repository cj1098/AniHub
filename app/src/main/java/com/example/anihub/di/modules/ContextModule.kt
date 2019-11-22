package com.example.anihub.di.modules

import android.content.Context
import com.example.anihub.AniHubApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module (includes = [AppModule::class])
class ContextModule {

    @Provides
    @Singleton
    fun providesContext(application: AniHubApplication): Context = application
}