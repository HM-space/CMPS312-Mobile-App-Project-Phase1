package com.example.lingosnack.screens.scoresScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingosnack.ui.theme.Tuna

@Composable
fun ScoreTopBar() {
    Text(
        text = "My Scores",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Tuna
            )
            .padding(vertical = 8.dp),
        color = Color.White
    )
}