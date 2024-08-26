package com.example.lingosnack.model

data class UserScore(
    val userEmail: String,
    val scores: MutableList<Score>
)
