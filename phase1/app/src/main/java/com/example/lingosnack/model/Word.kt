package com.example.lingosnack.model

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val text: String,
    var matched: Boolean = false,
    val definitions: List<Definition> = listOf(),
    val sentences: List<Sentence> = listOf(),
    val id: Int = 0
)