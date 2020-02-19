package com.example.anihub.ui.anime

import api.*
import com.example.anihub.ui.anime.AnimeModel.Media
import com.example.anihub.ui.anime.AnimeModel.PageInfo

/**
 * This class is used to transform data from the api into more view-friendly models.
 *
 */
class AnimeModelFactory {

    fun toAnimeModels(item: BrowseAnimeQuery.Data?): List<AnimeModel> {
        val animeList = mutableListOf<AnimeModel>()
        val pageInfo = item?.page?.pageInfo
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

                val animeModel = AnimeModel(it.id, PageInfo(pageInfo?.currentPage, pageInfo?.hasNextPage, pageInfo?.lastPage, pageInfo?.perPage, pageInfo?.total), media)
                animeList.add(animeModel)
            }

        }
        return animeList
    }

    fun toAnimeModel(item: SearchAnimeByIdQuery.Data?): AnimeModel? {
        var animeModel: AnimeModel? = null
        item?.media?.apply {
            val genres = genres?.filterNotNull()
            val title = title?.romaji ?: "No title available"
            val coverImage = arrayListOf(
                coverImage?.large,
                coverImage?.medium,
                coverImage?.extraLarge
            ).filterNotNull().first()
            val episodes = episodes ?: 0
            var studios: MutableList<AnimeModel.Studio> = mutableListOf()
            this.studios?.nodes?.toList()?.forEach {
                it?.let {
                    studios.add(AnimeModel.Studio(it.isAnimationStudio, it.name))
                }
            }
            val newStudios: AnimeModel.Studios = AnimeModel.Studios(studios)
            val media = Media(
                id = id,
                averageScore = averageScore ?: 0,
                genres = genres,
                coverImage = coverImage,
                episodes = episodes,
                startDate = startDate?.year ?: 0,
                studios = newStudios,
                title = title,
                description = description ?: ""
            )

            animeModel = AnimeModel(id, null, media)
        }
        return animeModel
    }

    fun toAnimeModel(item: SearchAnimeByIdEpisodeQuery.Data?): AnimeModel? {
        var animeModel: AnimeModel? = null
        item?.media?.let {
            var streamingEpisodes = arrayListOf<AnimeModel.StreamingEpisode>()
            it.streamingEpisodes?.forEach {episode ->
                val site = episode?.site ?: ""
                val thumbnail = episode?.thumbnail ?: "url here"
                val title = episode?.title ?: ""
                val url = episode?.url ?: ""
                streamingEpisodes.add(AnimeModel.StreamingEpisode(site, thumbnail, title, url))
            }
            val media = Media(
                id = it.id,
                streamingEpisodes = streamingEpisodes
            )
            animeModel = AnimeModel(it.id, null, media)
        }
        return animeModel
    }

//
//    fun toAnimeModel(item: SearchAnimeByGenresTagsQuery.Data?): AnimeModel {
//
//    }
//

//
//    fun toAnimeModel(item: SearchAnimeQuery.Data?): AnimeModel {
//
//    }
}