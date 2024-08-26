package com.example.lingosnack.screens.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.model.User
import com.example.lingosnack.ui.theme.RipePlum

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PackagesList(
    user: User?,
    packages: List<LearningPackage>,
    onSelectPackage: (Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(top = 10.dp),
        columns = GridCells.Adaptive(250.dp)
    ) {
        item(span = {
            GridItemSpan(maxLineSpan)
        })  {
            Text(
                text = "Packages",
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.headlineMedium,
                color = RipePlum
            )
        }
        items(packages) {
            PackageCard(it, user, onSelectPackage = { pkgId ->
                onSelectPackage(pkgId)
            })
        }
    }
}

