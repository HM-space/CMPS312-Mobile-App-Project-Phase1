package com.example.lingosnack.screens.gamesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.utils.GetCurrentRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesTopBar(navController: NavController, pkgName: String) {
    TopAppBar(
        title = {
            Text(
                text = pkgName,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Tuna,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        },
        navigationIcon = {
            if (!GetCurrentRoute(navController).toString().contains("games")) {
                IconButton(
                    onClick = { navController.navigateUp() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Tuna
        )
    )
}