package com.example.anihub.di.modules

import com.example.anihub.ui.anime.AnimeModelFactory
import dagger.Module
import dagger.Provides


@Module
class ModelModule {

    @Provides
    fun providesAnimeModelFactory(): AnimeModelFactory {
        return AnimeModelFactory()
    }
}