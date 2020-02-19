package com.example.anihub.ui.anime.shared

import android.util.Log
import api.*
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import javax.inject.Inject

class AnimeSharedRepository @Inject constructor(private val apolloClient: ApolloClient) {

    fun getAllAnime(page: Int): ApolloQueryCall<BrowseAnimeQuery.Data> {
        return apolloClient.query(BrowseAnimeQuery(page))
    }

    fun getAnimeById(id: Int): ApolloQueryCall<SearchAnimeByIdQuery.Data> {
        return apolloClient.query(SearchAnimeByIdQuery(id))
    }

    fun getAnimeByKeywords(page: Int, tags: List<String>, genres: List<String>): ApolloQueryCall<SearchAnimeByGenresTagsQuery.Data> {
        return apolloClient.query(SearchAnimeByGenresTagsQuery(page, tags, genres))
    }

    fun getAnimeEpisodesById(id: Int): ApolloQueryCall<SearchAnimeByIdEpisodeQuery.Data> {
        return apolloClient.query(SearchAnimeByIdEpisodeQuery(id))
    }

    fun getAnimeBySearchTerms(page: Int, terms: String): ApolloQueryCall<SearchAnimeQuery.Data> {
        return apolloClient.query(SearchAnimeQuery(page, terms))
    }
}