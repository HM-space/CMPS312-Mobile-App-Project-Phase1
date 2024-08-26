package com.example.lingosnack.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.model.User
import com.example.lingosnack.screens.games.flashCards.FlashCardsScreen
import com.example.lingosnack.screens.games.matchWords.MatchWordsScreen
import com.example.lingosnack.screens.gamesScreen.GamesScreen
import com.example.lingosnack.screens.homeScreen.HomeScreen
import com.example.lingosnack.screens.profileScreen.ProfileScreen
import com.example.lingosnack.screens.scoresScreen.ScoresScreen
import com.example.lingosnack.unscrambleSentences.UnscrambleSentenceScreen
import com.example.lingosnack.viewModel.PackagesViewModel
import com.example.lingosnack.viewModel.UsersViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(
    navController: NavHostController,
    padding: PaddingValues,
    packages: List<LearningPackage>,
    packagesViewModel: PackagesViewModel,
    usersViewModel: UsersViewModel,
    pkg: LearningPackage?,
    onSelectPkg: (LearningPackage) -> Unit
) {
    var user: User? by remember {
        mutableStateOf(null)
    }

    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route
    ) {
        composable(NavDestination.Games.route) {
            GamesScreen(padding, pkg) { gameName ->
                when (gameName) {
                    NavDestination.Flashcards.title -> navController.navigate("${NavDestination.Flashcards.route}/${pkg?.packageId}") {
                        popUpTo("settings")
                    }
                    NavDestination.UnscrambleSentences.title -> navController.navigate("${NavDestination.UnscrambleSentences.route}/${pkg?.packageId}") {
                        popUpTo("settings")
                    }
                    else -> navController.navigate("${NavDestination.Matching.route}/${pkg?.packageId}") {
                        popUpTo("settings")
                    }
                }
            }
        }
        composable(
            "games/{pkgId}",
            arguments = listOf(navArgument("pkgId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("pkgId")?.let { pkgId ->
                val learningPackage = packagesViewModel.getPackage(pkgId)
                GamesScreen(
                    padding,
                    learningPackage,
                ) { gameName ->
                    when (gameName) {
                        NavDestination.Flashcards.title -> navController.navigate("${NavDestination.Flashcards.route}/$pkgId") {
                            popUpTo("settings")
                        }
                        NavDestination.UnscrambleSentences.title -> navController.navigate("${NavDestination.UnscrambleSentences.route}/$pkgId") {
                            popUpTo("settings")
                        }
                        else -> navController.navigate("${NavDestination.Matching.route}/$pkgId") {
                            popUpTo("settings")
                        }
                    }
                }
            }
        }

        composable(NavDestination.Home.route) {
            HomeScreen(user, packages, padding, onSelectPackage = { pkgId ->
                navController.navigate("${NavDestination.Games.route}/$pkgId") {
                    popUpTo("settings")
                    onSelectPkg(packagesViewModel.getPackage(pkgId))
                }
            })
        }
        composable(NavDestination.Profile.route) {
            ProfileScreen(user, usersViewModel, padding, onUserLogin = {
                user = it
                navController.navigate(NavDestination.Home.route) {
                    popUpTo("settings")
                }
            })
        }

        composable(NavDestination.Scores.route) {
            ScoresScreen(
                usersViewModel,
                padding
            )
        }
        composable(
            "flashcards/{pkgId}",
            arguments = listOf(navArgument("pkgId") { type = NavType.IntType })
        ) { backStackEntry ->

            backStackEntry.arguments?.getInt("pkgId")?.let { pkgId ->
                val learningPackage = packagesViewModel.getPackage(pkgId)
                FlashCardsScreen(packagesViewModel, padding, learningPackage,
                    usersViewModel,
                    onNavigateBack = { navController.navigateUp() })
            }
        }
        composable(
            "unscrambleSent/{pkgId}",
            arguments = listOf(navArgument("pkgId") { type = NavType.IntType })
        ) { backStackEntry ->

            backStackEntry.arguments?.getInt("pkgId")?.let { pkgId ->
                val learningPackage = packagesViewModel.getPackage(pkgId)
                UnscrambleSentenceScreen(packagesViewModel, padding, learningPackage,
                    usersViewModel,
                    onNavigateBack = { navController.navigateUp() })
            }
        }
        composable(
            "matching/{pkgId}",
            arguments = listOf(navArgument("pkgId") { type = NavType.IntType })
        ) { backStackEntry ->

            backStackEntry.arguments?.getInt("pkgId")?.let { pkgId ->
                val learningPackage = packagesViewModel.getPackage(pkgId)
                MatchWordsScreen(packagesViewModel, usersViewModel, padding, learningPackage,
                    onNavigateBack = { navController.navigateUp() })
            }
        }
    }
}