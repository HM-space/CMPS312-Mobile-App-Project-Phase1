package com.example.lingosnack.model

import kotlinx.serialization.Serializable

@Serializable
data class Resource (
    val title: String,
    val url: String,
    val type: String
)