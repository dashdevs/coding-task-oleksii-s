package com.example.sorokayassirtest.data.net.adapter

import com.example.sorokayassirtest.BuildConfig

typealias NetworkModel = com.example.sorokayassirtest.data.net.entity.MovieApiModel
typealias DomainModel = com.example.sorokayassirtest.domain.entity.Movie

object FilmModelAdapter : NetworkModelAdapter<NetworkModel, DomainModel>() {

    override fun toDomainModel(networkEntity: NetworkModel) = with(networkEntity) {
        DomainModel(
            id = id,
            imageUrl = BuildConfig.BASE_IMAGE_URL + networkEntity.posterPath,
            title = title,
            description = overview,
            releaseDate = releaseDate ?: "",
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}