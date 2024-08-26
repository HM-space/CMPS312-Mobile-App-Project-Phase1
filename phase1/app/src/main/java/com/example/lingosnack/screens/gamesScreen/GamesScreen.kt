package com.example.lingosnack.screens.gamesScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingosnack.R
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.ui.theme.AstronautBlue
import com.example.lingosnack.ui.theme.BabyPink
import com.example.lingosnack.ui.theme.BeanRed
import com.example.lingosnack.ui.theme.LapisBlue
import com.example.lingosnack.ui.theme.LightSlateBlue
import com.example.lingosnack.ui.theme.PacificBlue
import com.example.lingosnack.ui.theme.ThulianPink
import com.example.lingosnack.ui.theme.Tuna

@Composable
fun GamesScreen(
    padding: PaddingValues,
    learningPackage: LearningPackage? = null,
    onSelectGame: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxHeight()
        .padding(padding)
    ) {
        if (learningPackage == null) {
            item {
                Surface(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(90.dp),
                    color = BeanRed,
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "No Package Selected :(",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                        )
                    }

                }
            }
        }
        item {
            GameCard(
                game = "Flash Cards",
                icon = R.drawable.cards,
                iconColor = ThulianPink,
                bgColor = BabyPink,
                learningPackage = learningPackage
            ) {
                onSelectGame(it)
            }
            GameCard(
                game = "Unscramble Sentences",
                icon = R.drawable.abc,
                iconColor = AstronautBlue,
                bgColor = PacificBlue,
                learningPackage = learningPackage
            ) {
                onSelectGame(it)
            }

        }
        item { GameCard(
            game = "Match Word and Definition",
            icon = R.drawable.puzzle,
            iconColor = LapisBlue,
            bgColor = LightSlateBlue,
            learningPackage = learningPackage
        ) {
            onSelectGame(it)
        } }
    }
}

@Composable
fun GameCard(
    game: String, icon: Int, iconColor: Color, bgColor: Color,
    learningPackage: LearningPackage?, onSelectGame: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = bgColor,
        ),
        modifier = Modifier
            .height(212.dp)
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = game,
                    fontSize = 24.sp,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    shape = RoundedCornerShape(6.dp),
                    enabled = learningPackage != null,
                    border = BorderStroke(width = 1.dp, color = Color.DarkGray),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Tuna,
                        containerColor = Color.White
                    ),
                    onClick = {
                        if (game.contains("Flash Cards")) {
                            onSelectGame("Flash Cards")
                        } else if (game.contains("Unscramble Sentences")) {
                            onSelectGame("Unscramble Sentences")
                        } else {
                            onSelectGame("Match Word and Definition")
                        }
                    }
                ) {
                    Text(
                        text = "Play",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Icon(
                    painter = painterResource(icon),
                    tint = iconColor,
                    contentDescription = null
                )
            }
        }
    }
}
