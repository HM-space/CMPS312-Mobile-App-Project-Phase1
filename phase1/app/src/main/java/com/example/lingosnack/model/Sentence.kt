package com.example.lingosnack.model

import kotlinx.serialization.Serializable

@Serializable
data class Sentence(
    val text: String,
    val resources: List<Resource> = listOf()
)