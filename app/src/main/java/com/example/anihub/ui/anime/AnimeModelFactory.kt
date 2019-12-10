package com.example.anihub.ui.anime

import api.*
import com.example.anihub.ui.anime.AnimeModel.Media
import com.example.anihub.ui.anime.AnimeModel.PageInfo

class AnimeModelFactory {

    fun toAnimeModels(item: BrowseAnimeQuery.Data?): List<AnimeModel> {
        val animeList = mutableListOf<AnimeModel>()
        item?.page?.media?.forEach {
            it?.apply {
                val genre = it.genres?.filterNotNull()
                val title = it.title?.romaji ?: "No title available"
                val coverImage = arrayListOf(it.coverImage?.large, it.coverImage?.medium, it.coverImage?.extraLarge).filterNotNull().first()
                val media = Media(
                    id = it.id,
                    averageScore = it.averageScore ?: 0,
                    genres = genre,
                    coverImage = coverImage,
                    title = title
                )

                val pageInfo = item.page.pageInfo

                val animeModel = AnimeModel(it.id, PageInfo(pageInfo?.currentPage, pageInfo?.hasNextPage, pageInfo?.lastPage, pageInfo?.perPage, pageInfo?.total), media)
                animeList.add(animeModel)
            }

        }
        return animeList
    }

//    fun toAnimeModel(item: SearchAnimeByIdQuery.Data?): AnimeModel {
//
//    }
//
//    fun toAnimeModel(item: SearchAnimeByGenresTagsQuery.Data?): AnimeModel {
//
//    }
//
//    fun toAnimeModel(item: SearchAnimeByIdEpisodeQuery.Data?): AnimeModel {
//
//    }
//
//    fun toAnimeModel(item: SearchAnimeQuery.Data?): AnimeModel {
//
//    }
}