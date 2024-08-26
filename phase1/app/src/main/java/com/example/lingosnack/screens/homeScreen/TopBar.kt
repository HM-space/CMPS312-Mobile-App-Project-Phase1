package com.example.lingosnack.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.PalePurple
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.viewModel.PackagesViewModel

@Composable
fun TopBar(
    viewModel: PackagesViewModel,
    updatePackages: (List<LearningPackage>) -> Unit
) {
    var query by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .background(
                color = Tuna
            )
            .padding(10.dp)
    ) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                updatePackages(viewModel.getFilteredPackages(query))
            },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Tuna) },
            trailingIcon = {
                if (query.isNotEmpty())
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Search", tint = Tuna,
                        modifier = Modifier.clickable {
                            query = ""
                            updatePackages(viewModel.getFilteredPackages(query))
                        })
            },


            modifier = Modifier
                .clip(shape = RoundedCornerShape(15.dp))
                .fillMaxWidth(),

            colors = TextFieldDefaults.colors(
                focusedContainerColor = LavendarMist,
                unfocusedContainerColor = LavendarMist,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = PalePurple,
                focusedLabelColor = PalePurple,
                unfocusedLabelColor = PalePurple
            ),
        )
    }
}