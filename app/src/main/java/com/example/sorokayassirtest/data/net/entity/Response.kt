package com.example.sorokayassirtest.data.net.entity

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Response(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val films: List<NetFilm>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)