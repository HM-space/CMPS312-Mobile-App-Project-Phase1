package com.example.lingosnack.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.utils.GetCurrentRoute


@Composable
fun AppNavigationRail(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    val currentRoute = GetCurrentRoute(navController)
    val navItems = listOf(
        NavDestination.Home,
        NavDestination.Games,
        NavDestination.Scores,
        NavDestination.Profile)

    NavigationRail(
        modifier = modifier
            .fillMaxHeight(),
        containerColor = Tuna,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxHeight()
        ) {
            navItems.forEach { navItem ->
                NavigationRailItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {

                            launchSingleTop = true
                        }
                    },
                    icon = {
                        val icon = navItem.icon ?: ImageVector.vectorResource(navItem.iconResourceId!!)
                        Icon(imageVector = icon, contentDescription = navItem.title)
                    },
                    label = {
                        Text(text = navItem.title, color = Color.White)
                    },
                    alwaysShowLabel = false,
                    colors = NavigationRailItemDefaults.colors(
                        indicatorColor = LavendarMist,
                        selectedIconColor = Tuna,
                        unselectedIconColor = Color.White
                    )
                )
            }
        }
    }
}