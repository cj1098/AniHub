package com.example.anihub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anihub.ui.anime.shared.AnimeSharedViewModel
import com.example.anihub.ui.anime.ViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AnimeSharedViewModel::class)
    abstract fun bindUserViewModel(userViewModel: AnimeSharedViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}