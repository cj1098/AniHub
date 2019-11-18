package com.example.anihub.dagger

import com.example.anihub.AniHubApplication
import com.example.anihub.MainActivity
import com.example.anihub.dagger.modules.ApiModule
import com.example.anihub.dagger.modules.AppModule
import com.example.anihub.dagger.modules.ContextModule
import com.example.anihub.ui.anime.AnimeListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ContextModule::class, AppModule::class, ApiModule::class]
)
interface AppComponent {
    fun inject(myApplication: AniHubApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(animeListFragment: AnimeListFragment)
}