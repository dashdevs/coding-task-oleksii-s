package com.example.sorokayassirtest.data.net.adapter

import com.example.sorokayassirtest.BuildConfig

typealias NetworkModel = com.example.sorokayassirtest.data.net.entity.NetFilm
typealias DomainModel = com.example.sorokayassirtest.domain.entity.Film

object FilmModelAdapter : NetworkModelAdapter<NetworkModel, DomainModel>() {

    override fun toDBModel(networkEntity: NetworkModel) = with(networkEntity) {
        DomainModel(
            id = id,
            imageUrl = BuildConfig.BASE_IMAGE_URL + networkEntity.poster_path,
            title = title,
            description = overview,
            releaseDate = release_date ?: "",
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}