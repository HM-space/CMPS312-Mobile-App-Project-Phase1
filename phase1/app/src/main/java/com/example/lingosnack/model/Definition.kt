package com.example.lingosnack.model

import kotlinx.serialization.Serializable

@Serializable
data class Definition (
    val text: String,
    val source: String
)