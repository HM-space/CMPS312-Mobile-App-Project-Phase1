package com.example.lingosnack.repo

import android.content.Context
import com.example.lingosnack.model.User
import kotlinx.serialization.json.Json

object UserRepo {

    private var users = mutableListOf<User>()

    fun getUsers(context: Context): List<User> {

        // populate the list when first time running the app
        if (users.isEmpty()) {
            // read the users.json data from the assets folder
            val jsonTextOfTheFile = context.assets
                .open("users.json")
                .bufferedReader()
                .use { it.readText() }

            // decode them to User Object
            // if there is a property not declared in my class just ignore it
            users = Json { ignoreUnknownKeys = true }
                .decodeFromString(jsonTextOfTheFile)
        }
        return users
    }

}