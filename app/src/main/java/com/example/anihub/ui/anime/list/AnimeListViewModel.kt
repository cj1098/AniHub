package com.example.anihub.ui.anime.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import api.BrowseAnimeQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import javax.inject.Inject

class AnimeListViewModel @Inject constructor(private val apolloClient: ApolloClient): ViewModel() {

    val browseAllAnimeLiveData: MutableLiveData<Response<BrowseAnimeQuery.Data>> = MutableLiveData()
    val browseAllAnimeError: MutableLiveData<String> = MutableLiveData()

    fun getAllAnime(page: Int) {
        apolloClient.query(BrowseAnimeQuery(page)).enqueue(object : ApolloCall.Callback<BrowseAnimeQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                browseAllAnimeError.postValue(e.message)
            }

            override fun onResponse(response: Response<BrowseAnimeQuery.Data>) {
                browseAllAnimeLiveData.postValue(response)
            }

        })
    }

}