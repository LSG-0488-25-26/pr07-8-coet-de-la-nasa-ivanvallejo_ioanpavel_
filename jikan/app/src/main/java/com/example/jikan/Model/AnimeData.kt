package com.example.jikan.Model

import com.google.gson.annotations.SerializedName

data class AnimeListResponse(
    val data: List<AnimeData>?,
    val pagination: Pagination?
)

data class AnimeResponse(
    val data: AnimeData?
)

data class AnimeData(
    @SerializedName("mal_id")
    val malId: Int,
    val url: String?,
    val images: Images?,
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("title_japanese")
    val titleJapanese: String?,
    val type: String?,
    val episodes: Int?,
    val status: String?,
    val score: Double?,
    @SerializedName("scored_by")
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val synopsis: String?,
    val year: Int?,
    val season: String?,
    val genres: List<Genre>?,
    val studios: List<Studio>?,
    val aired: Aired?
)

data class Images(
    val jpg: ImageFormat?
)

data class ImageFormat(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?
)

data class Genre(
    @SerializedName("mal_id")
    val malId: Int,
    val name: String
)

data class Studio(
    @SerializedName("mal_id")
    val malId: Int,
    val name: String
)

data class Aired(
    val from: String?,
    val to: String?
)

data class Pagination(
    @SerializedName("has_next_page")
    val hasNextPage: Boolean
)