package com.example.lingosnack

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.navigation.AppNavigationRail
import com.example.lingosnack.navigation.AppNavigator
import com.example.lingosnack.navigation.BottomNavBar
import com.example.lingosnack.screens.gamesScreen.GamesTopBar
import com.example.lingosnack.screens.homeScreen.TopBar
import com.example.lingosnack.screens.scoresScreen.ScoreTopBar
import com.example.lingosnack.ui.theme.LingoSnackTheme
import com.example.lingosnack.utils.ExtractId
import com.example.lingosnack.utils.GetCurrentRoute
import com.example.lingosnack.viewModel.PackagesViewModel
import com.example.lingosnack.viewModel.UsersViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LingoSnackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LingoSnacks()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalLayoutApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LingoSnacks(modifier: Modifier = Modifier) {
    val context = LocalContext.current as Activity
    val windowSizeClass = calculateWindowSizeClass(context)

    val showBottomBar = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val showNavRail = !showBottomBar

    val navController = rememberNavController()
    val packagesViewModel = viewModel<PackagesViewModel>()
    val usersViewModel = viewModel<UsersViewModel>()

    var packages by remember {
        mutableStateOf(packagesViewModel.packages)
    }
    var pkg: LearningPackage? by remember {
        mutableStateOf(null)
    }

    val currRoute = GetCurrentRoute(navController)
    val pkgId = ExtractId(navController = navController).toString()

    Scaffold(
        topBar = {
            when (currRoute) {
                "home" -> TopBar(viewModel = packagesViewModel) {
                    packages = it
                }
                "scores" -> ScoreTopBar()
                "games" -> pkg?.let {
                    GamesTopBar(navController = navController, pkgName = it.title)
                }
                "games/{pkgId}",
                "unscrambleSent/{pkgId}",
                "matching/{pkgId}",
                "flashcards/{pkgId}" -> GamesTopBar(
                    navController,
                    packagesViewModel.getPackage(pkgId.toInt()).title
                )
            }
        },
        bottomBar = {
            if (showBottomBar)
                BottomNavBar(navController)
        }
    ) { paddingValues ->
        Row(
            Modifier
                .fillMaxSize()
                .consumeWindowInsets(paddingValues)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            if (showNavRail) {
                AppNavigationRail(navController)
            }
            AppNavigator(
                navController, paddingValues, packages,
                packagesViewModel, usersViewModel,
                pkg, onSelectPkg = { pkg = it }
            )
        }
    }
}