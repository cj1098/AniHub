package com.example.anihub.di

import com.example.anihub.AniHubApplication
import com.example.anihub.MainActivity
import com.example.anihub.di.modules.*
import com.example.anihub.ui.anime.list.AnimeListFragment
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import com.example.anihub.ui.anime.ViewModelFactory
import com.example.anihub.ui.anime.detail.AnimeActivity
import com.example.anihub.ui.anime.detail.AnimeDetailEpisodesFragment
import com.example.anihub.ui.anime.detail.AnimeDetailOverviewFragment
import com.example.anihub.ui.anime.detail.AnimeDetailSeeMoreFragment
import com.example.anihub.ui.search.SearchAnimeFragment
import dagger.Component
import javax.inject.Singleton


@Component(
    modules = [CacheModule::class, AppModule::class, ApiModule::class, ContextModule::class, ModelModule::class])
@Singleton
interface AppComponent{

    fun inject(myApplication: AniHubApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(animeActivity: AnimeActivity)
    fun inject(animeListFragment: AnimeListFragment)
    fun inject(animeSharedViewModel: AnimeSharedViewModel)
    fun inject(viewModelFactory: ViewModelFactory)
    fun inject(animeDetailOverviewFragment: AnimeDetailOverviewFragment)
    fun inject(animeDetailEpisodesFragment: AnimeDetailEpisodesFragment)
    fun inject(animeDetailSeeMoreFragment: AnimeDetailSeeMoreFragment)
    fun inject(searchAnimeFragment: SearchAnimeFragment)
}