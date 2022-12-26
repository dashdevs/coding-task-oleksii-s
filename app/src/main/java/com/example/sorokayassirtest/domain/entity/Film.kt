package com.example.sorokayassirtest.domain.entity

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Film(
    @Json(name = "id") val id: Int,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "vote_count") val voteCount: Int,
) : Item