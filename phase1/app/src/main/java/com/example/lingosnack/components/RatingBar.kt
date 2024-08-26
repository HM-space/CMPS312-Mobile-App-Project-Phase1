package com.example.lingosnack.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {

    Row {
        repeat(5) {
            IconButton(
                modifier = Modifier.size(40.dp),
                onClick = {
                    onRatingChanged(it + 1)
                }
            ) {
                Icon(
                    Icons.Default.Star, null,
                    tint = if (it < rating) Color(0xFFF2EB31) else Color.LightGray
                )
            }
        }
    }
}