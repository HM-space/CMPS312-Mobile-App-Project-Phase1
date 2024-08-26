package com.example.lingosnack.screens.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.model.User
import com.example.lingosnack.ui.theme.Orchid
import com.example.lingosnack.ui.theme.RipePlum

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(user: User?,
               packages: List<LearningPackage>,
               paddingValues: PaddingValues,
               onSelectPackage: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(start = 10.dp, end = 10.dp)
    ) {
            Text(
                text = "Welcome to LingoSnacks!",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.headlineMedium,
                color = RipePlum
            )
            if(user != null) {
                Text(
                    text = "Hello ${user.firstName}",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.headlineMedium,
                    color = RipePlum
                )
            }
            Divider(color = Orchid, thickness = 2.dp)

            PackagesList(user, packages) { pkgId ->
                onSelectPackage(pkgId)
            }


    }
}