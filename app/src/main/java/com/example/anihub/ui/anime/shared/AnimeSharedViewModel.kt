package com.example.anihub.ui.anime.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import api.BrowseAnimeQuery
import api.SearchAnimeByGenresTagsQuery
import api.SearchAnimeByIdEpisodeQuery
import api.SearchAnimeByIdQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import java.lang.Exception
import javax.inject.Inject

class AnimeSharedViewModel @Inject constructor(private val animeSharedRepository: AnimeSharedRepository) :
    ViewModel() {

    // live data
    val browseAllAnimeLiveData: MutableLiveData<Response<BrowseAnimeQuery.Data>> = MutableLiveData()
    val searchAnimeByIdLiveData: MutableLiveData<Response<SearchAnimeByIdQuery.Data>> = MutableLiveData()
    val searchAnimeByGenresTagsLiveData: MutableLiveData<Response<SearchAnimeByGenresTagsQuery.Data>> = MutableLiveData()
    val searchAnimeEpisodesByIdLiveData: MutableLiveData<Response<SearchAnimeByIdEpisodeQuery.Data>> = MutableLiveData()
    val animeError: MutableLiveData<Exception> = MutableLiveData()

    fun loadAllAnime(page: Int) {
        animeSharedRepository.getAllAnime(page)
            .enqueue(object : ApolloCall.Callback<BrowseAnimeQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    animeError.postValue(e)
                }

                override fun onResponse(response: Response<BrowseAnimeQuery.Data>) {
                    // manipulate our responses here so that they never send a nullable value if possible.
                    browseAllAnimeLiveData.postValue(response)
                }
            })
    }

    fun loadAnimeById(id: Int) {
        animeSharedRepository.getAnimeById(id)
            .enqueue(object : ApolloCall.Callback<SearchAnimeByIdQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    animeError.postValue(e)
                }

                override fun onResponse(response: Response<SearchAnimeByIdQuery.Data>) {
                    searchAnimeByIdLiveData.postValue(response)
                }
            })
    }

    fun loadAnimeEpisodesById(id: Int) {
        animeSharedRepository.getAnimeEpisodesById(id)
            .enqueue(object : ApolloCall.Callback<SearchAnimeByIdEpisodeQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    animeError.postValue(e)
                }

                override fun onResponse(response: Response<SearchAnimeByIdEpisodeQuery.Data>) {
                    searchAnimeEpisodesByIdLiveData.postValue(response)
                }
            })
    }

    fun loadAnimeByKeywords(page: Int, tags: ArrayList<String>, genres: ArrayList<String>) {
        // Need to get the page and tags/genres :(
        animeSharedRepository.getAnimeByKeywords(page, tags, genres)
            .enqueue(object : ApolloCall.Callback<SearchAnimeByGenresTagsQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    animeError.postValue(e)
                }

                override fun onResponse(response: Response<SearchAnimeByGenresTagsQuery.Data>) {
                    searchAnimeByGenresTagsLiveData.postValue(response)
                }
            })
    }
}