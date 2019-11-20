package com.example.anihub.di

import com.example.anihub.AniHubApplication
import com.example.anihub.MainActivity
import com.example.anihub.di.modules.ApiModule
import com.example.anihub.di.modules.AppModule
import com.example.anihub.di.modules.ContextModule
import com.example.anihub.ui.anime.list.AnimeListFragment
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.detail.AnimeActivity
import com.example.anihub.ui.anime.detail.AnimeDetailFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ContextModule::class, AppModule::class, ApiModule::class]
)
// Revisit this and make it more scalable...
interface AppComponent {
    fun inject(myApplication: AniHubApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(animeListFragment: AnimeListFragment)
    fun inject(animeSharedViewModel: AnimeSharedViewModel)
    fun inject(viewModelFactory: ViewModelFactory)
    fun inject(animeFragment: AnimeActivity)
    fun inject(animeDetailFragment: AnimeDetailFragment)
}