package com.example.anihub.ui.anime.shared

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.*
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloCallback
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.anihub.ui.anime.AnimeModel
import com.example.anihub.ui.anime.AnimeModelFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class AnimeSharedViewModel @Inject constructor(private val animeSharedRepository: AnimeSharedRepository,
                                               private val modelFactory: AnimeModelFactory) :
    ViewModel() {

    // live data
    val browseAllAnimeLiveData: MutableLiveData<List<AnimeModel>> = MutableLiveData()
    val searchAnimeByIdLiveData: MutableLiveData<AnimeModel> = MutableLiveData()
    val searchAnimeEpisodesByIdLiveData: MutableLiveData<AnimeModel> = MutableLiveData()
    val searchAnimeByGenresTagsLiveData: MutableLiveData<Response<SearchAnimeByGenresTagsQuery.Data>> = MutableLiveData()
    val searchAnimeLiveData: MutableLiveData<Response<SearchAnimeQuery.Data>> = MutableLiveData()
    val animeError: MutableLiveData<Exception> = MutableLiveData()

    fun loadAllAnime(page: Int) {
        animeSharedRepository.getAllAnime(page)
            .enqueue(object : ApolloCall.Callback<BrowseAnimeQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    animeError.postValue(e)
                }

                override fun onResponse(response: Response<BrowseAnimeQuery.Data>) {
                    Log.d("chris", response.data()?.page?.pageInfo?.total.toString())
                    // manipulate our responses here so that they never send a nullable value if possible.
                    val animeModels = modelFactory.toAnimeModels(response.data())
                    browseAllAnimeLiveData.postValue(animeModels)
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
                    val animeModel = modelFactory.toAnimeModel(response.data())

                    searchAnimeByIdLiveData.postValue(animeModel)
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
                    val animeModel = modelFactory.toAnimeModel(response.data())
                    searchAnimeEpisodesByIdLiveData.postValue(animeModel)
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

    fun loadAnimeBySearchTerms(page: Int, terms: String) {
        animeSharedRepository.getAnimeBySearchTerms(page, terms)
            .enqueue(object : ApolloCall.Callback<SearchAnimeQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    animeError.postValue(e)
                }

                override fun onResponse(response: Response<SearchAnimeQuery.Data>) {
                    searchAnimeLiveData.postValue(response)
                }
            })
    }

}