package com.example.lingosnack.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating (
    val comment: String,
    val doneAt: kotlinx.datetime.LocalDateTime,
    val doneBy: String,
    val rating: Int
    )