package com.example.lingosnack.screens.games.matchWords

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.viewModel.PackagesViewModel
import com.example.lingosnack.viewModel.UsersViewModel

@Composable
fun MatchWordsScreen(
    viewModel: PackagesViewModel,
    usersViewModel: UsersViewModel,
    padding: PaddingValues,
    learningPackage: LearningPackage,
    onNavigateBack: () -> Unit
) {
    usersViewModel.addUserScore(learningPackage.packageId,learningPackage.title)
    MatchWordsGame(
        originalWords = viewModel.getPackage(learningPackage.packageId).words,
        usersViewModel, padding, learningPackage.packageId, learningPackage.title
    )
}