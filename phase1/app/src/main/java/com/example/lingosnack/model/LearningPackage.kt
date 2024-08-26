package com.example.lingosnack.model

import kotlinx.serialization.Serializable

@Serializable
data class LearningPackage (
    val packageId: Int,
    val author: String,
    val category: String,
    val description: String,
    val iconUrl: String = "",
    val language: String,
    val lastUpdatedDate: String,
    val level: String,
    val title: String,
    val version: Int,
    val words: List<Word>,
    val ratings: MutableList<Rating> = mutableListOf<Rating>()
) {
    fun getAverageRating(): Double {
        if (ratings.size == 0) {
            return 0.0
        }
        return (ratings.map {it.rating}
            .reduce{sum, rating -> sum + rating}).toDouble() / ratings.size
    }
}







