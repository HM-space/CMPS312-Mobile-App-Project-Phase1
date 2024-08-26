package com.example.lingosnack.model

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val photoUri: String,
    val role: String
)