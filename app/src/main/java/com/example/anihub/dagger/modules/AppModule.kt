package com.example.anihub.dagger.modules

import com.example.anihub.AniHubApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: AniHubApplication) {
    @Singleton
    @Provides
    fun provideApp() = application
}