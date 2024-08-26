package com.example.lingosnack.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId: Int? = null) {
    object Home: NavDestination(route = "home", title = "Home", icon = Icons.Default.Star)
    object Games: NavDestination(route = "games", title = "Games", icon = Icons.Default.VideogameAsset)
    object Scores: NavDestination(route = "scores", title = "Scores", icon = Icons.Outlined.ContentPaste)
    object Profile: NavDestination(route = "profile", title = "Profile", icon = Icons.Outlined.AccountCircle)


    object Flashcards: NavDestination(route = "flashcards", title = "Flash Cards")
    object UnscrambleSentences: NavDestination(route = "unscrambleSent", title = "Unscramble Sentences")
    object Matching: NavDestination(route = "matching", title = "Match Word and Definition")


}