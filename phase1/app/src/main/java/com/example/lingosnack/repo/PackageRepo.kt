package com.example.lingosnack.repo

import android.content.Context
import com.example.lingosnack.model.LearningPackage
import kotlinx.serialization.json.Json

object PackageRepo {

    private var packages = listOf<LearningPackage>()

    fun getPackages(context: Context): List<LearningPackage> {
        if (packages.isEmpty()) {
            val jsonTextOfTheFile = context.assets
                .open("packages.json")
                .bufferedReader()
                .use { it.readText() }
            packages = Json { ignoreUnknownKeys = true }
                .decodeFromString(jsonTextOfTheFile)
        }
        return packages
    }

}