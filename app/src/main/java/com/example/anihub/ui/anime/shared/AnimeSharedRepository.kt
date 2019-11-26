package com.example.anihub.ui.anime.shared

import api.BrowseAnimeQuery
import api.SearchAnimeByGenresTagsQuery
import api.SearchAnimeByIdEpisodeQuery
import api.SearchAnimeByIdQuery
import api.type.MediaType
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCache
import com.example.anihub.CacheFactory
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
}