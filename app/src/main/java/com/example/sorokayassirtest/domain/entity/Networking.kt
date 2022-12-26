package com.example.sorokayassirtest.domain.entity

data class Networking(
    val message: String,
    val isItError: Boolean = false
) : Item
