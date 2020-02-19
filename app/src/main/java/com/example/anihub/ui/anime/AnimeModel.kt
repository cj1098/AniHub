package com.example.anihub.ui.anime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime")
data class AnimeModel(@PrimaryKey(autoGenerate = true) val id: Int,
                      val pageInfo: PageInfo? = null,
                      val media: Media) {


    data class PageInfo(
        val currentPage: Int?,
        val hasNextPage: Boolean?,
        val lastPage: Int?,
        val perPage: Int?,
        val total: Int?
    )

    data class Media(
        val averageScore: Int = 0,
        val bannerImage: String = "",
        val chapters: Int = 0,
        val characters: Characters? = null,
        val coverImage: String = "",
        val description: String = "",
        val endDate: String = "",
        val episodes: Int = 0,
        val externalLinks: List<ExternalLink>? = null,
        val format: String = "",
        val genres: List<String>? = null,
        val hashtag: String = "",
        val id: Int,
        val isAdult: Boolean = false,
        val isFavourite: Boolean = false,
        val meanScore: Int = 0,
        val rankings: List<Ranking>? = null,
        val relations: Relations? = null,
        val reviews: Reviews? = null,
        val season: String = "",
        val source: String = "",
        val staff: Staff? = null,
        val startDate: Int? = null,
        val stats: Stats? = null,
        val status: String = "",
        val streamingEpisodes: List<StreamingEpisode>? = null,
        val studios: Studios? = null,
        val synonyms: List<String>? = null,
        val tags: List<Tag>? = null,
        val title: String = "",
        val trailer: Trailer? = null,
        val type: String = "",
        val volumes: Int = 0
    )

    data class Trailer(
        val thumbnail: String,
        val site: String
    )

    data class Characters(
        val edges: List<Edge>
    )

    data class Edge(
        val node: Node,
        val role: String,
        val voiceActors: List<VoiceActor>
    )

    data class Node(
        val id: Int,
        val image: Image,
        val name: Name
    )

    data class Image(
        val large: String,
        val medium: String
    )

    data class Name(
        val first: String,
        val last: Any
    )

    data class VoiceActor(
        val id: Int,
        val image: ImageX,
        val language: String,
        val name: NameX
    )

    data class ImageX(
        val large: String,
        val medium: String
    )

    data class NameX(
        val first: String,
        val last: String,
        val native: String
    )

    data class CoverImage(
        val large: String,
        val medium: String
    )

    data class EndDate(
        val day: Int,
        val month: Int,
        val year: Int
    )

    data class ExternalLink(
        val id: Int,
        val site: String,
        val url: String
    )

    data class Ranking(
        val allTime: Boolean,
        val context: String,
        val format: String,
        val id: Int,
        val rank: Int,
        val season: Any,
        val type: String,
        val year: Int
    )

    data class Relations(
        val edges: List<EdgeX>
    )

    data class EdgeX(
        val node: NodeX,
        val relationType: String
    )

    data class NodeX(
        val id: Int,
        val title: Title
    )

    data class Title(
        val english: String,
        val native: String,
        val romaji: String
    )

    data class Reviews(
        val nodes: List<NodeXX>
    )

    data class NodeXX(
        val id: Int,
        val rating: Int,
        val ratingAmount: Int,
        val summary: String,
        val userId: Int
    )

    data class Staff(
        val edges: List<EdgeXX>
    )

    data class EdgeXX(
        val node: NodeXXX,
        val role: String
    )

    data class NodeXXX(
        val id: Int,
        val image: ImageXX,
        val name: NameXX
    )

    data class ImageXX(
        val large: String,
        val medium: String
    )

    data class NameXX(
        val first: String,
        val last: String,
        val native: String
    )

    data class StartDate(
        val day: Int,
        val month: Int,
        val year: Int
    )

    data class Stats(
        val airingProgression: Any,
        val scoreDistribution: List<ScoreDistribution>,
        val statusDistribution: List<StatusDistribution>
    )

    data class ScoreDistribution(
        val amount: Int,
        val score: Int
    )

    data class StatusDistribution(
        val amount: Int,
        val status: String
    )

    data class StreamingEpisode(
        val site: String,
        val thumbnail: String,
        val title: String,
        val url: String
    )

    data class Studios(
        val nodes: List<Studio>
    )

    data class Studio(
        val isAnimationStudio: Boolean,
        val name: String
    )

    data class Tag(
        val category: String,
        val description: String,
        val id: Int,
        val isAdult: Boolean,
        val isGeneralSpoiler: Boolean,
        val isMediaSpoiler: Boolean,
        val name: String,
        val rank: Int
    )

    data class TitleX(
        val english: String,
        val native: String,
        val romaji: String,
        val userPreferred: String
    )
}