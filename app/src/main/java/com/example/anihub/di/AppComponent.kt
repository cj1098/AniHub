package com.example.anihub.di

import com.example.anihub.AniHubApplication
import com.example.anihub.MainActivity
import com.example.anihub.di.modules.ApiModule
import com.example.anihub.di.modules.AppModule
import com.example.anihub.di.modules.ContextModule
import com.example.anihub.ui.anime.list.AnimeListFragment
import com.example.anihub.ui.anime.list.AnimeListViewModel
import com.example.anihub.ui.anime.ViewModelFactory
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
    fun inject(animeListViewModel: AnimeListViewModel)
    fun inject(viewModelFactory: ViewModelFactory)
}