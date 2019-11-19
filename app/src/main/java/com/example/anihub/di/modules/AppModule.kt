package com.example.anihub.di.modules

import com.example.anihub.AniHubApplication
import com.example.anihub.di.ViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule(private val application: AniHubApplication) {
    @Singleton
    @Provides
    fun provideApp() = application
}