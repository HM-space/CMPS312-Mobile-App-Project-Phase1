package com.example.lingosnack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.repo.PackageRepo

class PackagesViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val _packages by lazy {
        PackageRepo.getPackages(appContext)
    }

    val packages: List<LearningPackage>
        get() = _packages

    fun getFilteredPackages(query: String): List<LearningPackage> {
        val filteredByLevel = _packages.filter {
            it.level.contains(query, ignoreCase = true)
        }.toMutableList()

        val filteredByWord = _packages.filter { pkg ->
            pkg.words.any { wrd -> wrd.text.contains(query, ignoreCase = true) }
        }

        val filteredByPackageName = _packages.filter {
            it.title.contains(query, ignoreCase = true)
        }

        filteredByLevel.addAll(filteredByWord)
        filteredByLevel.addAll(filteredByPackageName)
        return filteredByLevel.distinct()
    }

    fun getPackage(pkgId: Int): LearningPackage {
        return _packages.filter { it.packageId == pkgId }[0]
    }
    fun getSentences(pkgId: Int): List<String> {
        val pkg = getPackage(pkgId)
        val sentences = mutableListOf<String>()
        pkg.words.forEach { wrd -> wrd.sentences.forEach { sntns -> sentences.add(sntns.text) } }
        return sentences.toList()
    }
    
}