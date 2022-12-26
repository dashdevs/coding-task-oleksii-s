package com.example.sorokayassirtest.data.net

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import se.ansman.kotshi.KotshiJsonAdapterFactory

@KotshiJsonAdapterFactory
object ApplicationJsonAdapterFactory : JsonAdapter.Factory by KotshiApplicationJsonAdapterFactory

val moshi: Moshi = Moshi.Builder()
    .add(ApplicationJsonAdapterFactory)
    .build()
