package com.example.lingosnack.screens.games.matchWords

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingosnack.components.AlertDialog
import com.example.lingosnack.components.DragTarget
import com.example.lingosnack.components.DropTarget
import com.example.lingosnack.components.LongPressDraggable
import com.example.lingosnack.model.Word
import com.example.lingosnack.ui.theme.LapisBlue
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.PalePurple
import com.example.lingosnack.ui.theme.Purple40
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.viewModel.UsersViewModel

@Composable
fun MatchWordsGame(
    originalWords: List<Word>,
    usersViewModel: UsersViewModel,
    paddingValues: PaddingValues = PaddingValues(20.dp),
    packageId: Int,
    packageName: String
) {

    val words = remember {
        mutableStateListOf(
            *originalWords.mapIndexed { index, word ->
                Word(
                    text = word.text,
                    definitions = listOf(
                        word.definitions.shuffled()[0]
                    ),
                    id = index
                )
            }.toTypedArray()
        )
    }

    val shuffledWords = remember {
        words.shuffled().toMutableStateList()
    }

    LaunchedEffect(Unit) {
        usersViewModel.addUserScore(packageId,packageName,matchQuestions = words.size)
    }

    var score by remember { mutableStateOf(0) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(score) {
        if (score/2 == words.size)
            showDialog = true
    }

    Column (
        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
    ) {
        Text(
            text = "Score: ${score/2}",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Tuna,
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp)
                .align(Alignment.End)
                .border(
                    BorderStroke(2.dp, PalePurple),
                    shape = RoundedCornerShape(10.dp)
                )
                .background(
                    color = LavendarMist,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 8.dp)
        )
        Text(
            text = "Match the words to their definitions:",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = LapisBlue,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 6.dp)
        )
        Row (
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Word",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth(0.4F)
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .border(
                        BorderStroke(2.dp, LapisBlue),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(
                        color = Purple40,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(4.dp)
            )
            Text(
                text = "Definition",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .border(
                        BorderStroke(2.dp, LapisBlue),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(
                        color = Purple40,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(4.dp)
            )
        }
        LongPressDraggable(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                WordList(shuffledWords)
                DefinitionsList(words) {
                    var index = words.indexOfFirst { w -> w.id == it }
                    if (index >= 0) {
                        words[index] = words[index].copy(matched = true)
                    }
                    index = shuffledWords.indexOfFirst { w -> w.id == it }
                    if (index >= 0) {
                        shuffledWords[index] = shuffledWords[index].copy(matched = true)
                        score++
                        if (score % 2 == 0)
                            usersViewModel.addUserScore(packageId, packageName, matchScore = 1)
                    }


                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onShowDialogChange = { showDialog = it },
                title = "Congratulations!",
                message = "Your score is: ${score/2}",
            )
        }
    }
}

@Composable
fun WordList(words: List<Word>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(),
    ) {
        items(words) {
            if (!it.matched) {
                DragTarget(dataToDrop = it, modifier = Modifier.padding(10.dp)) {
                    SuggestionChip(onClick = {  },
                        label = {
                            Text(
                                text = it.text,
                            )
                        })
                }
            }
        }
    }
}

@Composable
fun DefinitionsList(words: List<Word>, onDrop: (wordIndex: Int)->Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        items(words) {

            DropTarget<Word>(
                modifier = Modifier.padding(6.dp)
            ) { isInBound, word ->
                val bgColor = if (isInBound) {
                    Color.Yellow
                } else {
                    Color.White
                }

                println("Word: $word")

                if (word != null) {
                    if (isInBound && word.id == it.id) {
                        onDrop(it.id)
                    }
                }

                WordCard(word = it, bgColor = bgColor)
            }
        }
    }
}

@Composable
fun WordCard(word: Word, bgColor: Color) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
        .background(bgColor, RoundedCornerShape(16.dp)),
    ) {
        if (word.matched) {
            Text(text = "${word.text} - ${word.definitions[0].text}",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth() )
        } else {
            Text(
                text = word.definitions[0].text,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp)
            )
        }
    }
}