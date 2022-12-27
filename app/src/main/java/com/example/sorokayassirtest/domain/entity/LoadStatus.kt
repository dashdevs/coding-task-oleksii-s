package com.example.sorokayassirtest.domain.entity

open class LoadStatus<out T : Any> {
    data class Success<out T : Any>(val data: T) : LoadStatus<T>()
    data class Error(val e: Exception) : LoadStatus<Nothing>()
}