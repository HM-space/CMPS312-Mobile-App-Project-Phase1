package com.example.lingosnack.screens.scoresScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingosnack.model.Score
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.PalePurple
import com.example.lingosnack.ui.theme.Purple40
import com.example.lingosnack.ui.theme.Purple80
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.viewModel.UsersViewModel

@Composable
fun ScoresScreen(
    usersViewModel: UsersViewModel,
    padding: PaddingValues
) {
    Column (
        modifier = Modifier.padding(padding)
    ) {
        Card (
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = LavendarMist),
            border = BorderStroke(width = 2.dp, color = PalePurple)
        ) {
            Row {
                Text(
                    text = "Total Score: ",
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 25.sp,
                    color = Tuna
                )
                Text(
                    text = String.format("%.1f%s",usersViewModel.getTotalScore(),"%"),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    textAlign = TextAlign.End,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Tuna
                )
            }
        }
        ScoreCardList(usersViewModel.getScores())
    }
}

@Composable
fun ScoreCardList(currUserScores: MutableList<Score>) {
    LazyColumn {
        items(currUserScores) {
            ScoreCard(it)
        }
    }
}

@Composable
fun ScoreCard(packageScore: Score) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Purple80),
        border = BorderStroke(2.dp, Purple40)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = packageScore.packageName,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                color = Purple40,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 1.dp)
                    .fillMaxWidth(0.55f),
                thickness = 1.dp,
                color = Purple40
            )
            Row {
                Text(
                    text = "Unscramble Sentences",
                    color = Purple40,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = String.format("%.1f%s",packageScore.getUnscrambleScore(),"%"),
                    color = Purple40,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            Row {
                Text(
                    text = "Match Words & Definitions",
                    color = Purple40,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = String.format("%.1f%s",packageScore.getMatchScore(),"%"),
                    color = Purple40,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}