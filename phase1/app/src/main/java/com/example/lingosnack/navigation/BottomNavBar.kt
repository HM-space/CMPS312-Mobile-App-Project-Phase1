package com.example.lingosnack.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.utils.GetCurrentRoute

@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar(
        containerColor = Tuna,
        modifier = Modifier.clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        val currentRoute = GetCurrentRoute(navController)
        val navItems = listOf(
            NavDestination.Home,
            NavDestination.Games,
            NavDestination.Scores,
            NavDestination.Profile
        )
        navItems.forEach { navItem ->
            NavigationBarItem(
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
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = LavendarMist,
                    selectedIconColor = Tuna,
                    unselectedIconColor = Color.White
                )
            )
        }
    }
}