package com.example.anihub.ui.anime.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import api.BrowseAnimeQuery
import api.SearchAnimeByIdQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import java.lang.Exception
import javax.inject.Inject

class AnimeSharedViewModel @Inject constructor(private val animeSharedRepository: AnimeSharedRepository): ViewModel() {

    val browseAllAnimeLiveData: MutableLiveData<Response<BrowseAnimeQuery.Data>> = MutableLiveData()
    val searchAnimeByIdLiveData: MutableLiveData<Response<SearchAnimeByIdQuery.Data>> = MutableLiveData()
    val animeError: MutableLiveData<Exception> = MutableLiveData()

    fun loadAllAnime(page: Int) {
        animeSharedRepository.getAllAnime(page).enqueue(object : ApolloCall.Callback<BrowseAnimeQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                animeError.postValue(e)
            }

            override fun onResponse(response: Response<BrowseAnimeQuery.Data>) {
                browseAllAnimeLiveData.postValue(response)
            }
        })
    }

    fun loadAnimeById(id: Int) {
        animeSharedRepository.getAnimeById(id).enqueue(object : ApolloCall.Callback<SearchAnimeByIdQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                animeError.postValue(e)
            }

            override fun onResponse(response: Response<SearchAnimeByIdQuery.Data>) {
                searchAnimeByIdLiveData.postValue(response)
            }
        })
    }
}