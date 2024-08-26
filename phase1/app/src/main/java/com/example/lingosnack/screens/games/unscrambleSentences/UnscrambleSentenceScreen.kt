package com.example.lingosnack.unscrambleSentences

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.example.lingosnack.components.Pager
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.viewModel.PackagesViewModel
import com.example.lingosnack.viewModel.UsersViewModel


@Composable
fun UnscrambleSentenceScreen(
    viewModel: PackagesViewModel,
    padding: PaddingValues,
    learningPackage: LearningPackage,
    usersViewModel: UsersViewModel,
    onNavigateBack: () -> Unit
) {
    Pager(
        viewModel = viewModel,
        useCase = "UnscrambleGame",
        padding = padding,
        learningPackage = learningPackage,
        usersViewModel = usersViewModel
    )
}

