package com.example.sorokayassirtest.data.net.adapter

abstract class NetworkModelAdapter<in N, out D> {

    abstract fun toDomainModel(networkEntity: N): D

    fun List<N>.convertToDomainModel(): List<D> = ArrayList<D>(size).apply {
        this@convertToDomainModel.forEach { add(toDomainModel(it)) }
    }
}