package com.example.sorokayassirtest.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val description: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
) : Parcelable