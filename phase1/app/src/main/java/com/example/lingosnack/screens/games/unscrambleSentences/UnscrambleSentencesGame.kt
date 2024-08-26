package com.example.lingosnack.screens.games.unscrambleSentences

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.LightSlateBlue
import com.example.lingosnack.ui.theme.PalePurple
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.viewModel.UsersViewModel


@Composable
fun UnscrambleSentenceGame(
    sentence: String,
    padding: PaddingValues,
    usersViewModel: UsersViewModel,
    packageId: Int,
    packageName: String,
    startScore: Int,
) {
    val originalSent = sentence.split(" ")
        .mapIndexed { index, wordText ->
            Word(text = wordText, id = index + 1)
        }

    val originalSentList = remember {
        mutableStateListOf(*originalSent.toTypedArray())
    }
    val shuffledSentence = remember {
        originalSentList.shuffled().toMutableStateList()
    }

    var resetTrigger by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(startScore) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(score) {
        if (score == originalSentList.size) {
            showDialog = true
        } else if (score == 1) {
            usersViewModel.addUserScore(
                packageId,
                packageName,
                unScrambleQuestions = originalSent.size
            )
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(padding)
    ) {
        item {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Score: $score",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Tuna,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
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
            }
        }
        item {
            LongPressDraggable(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShuffledWordsList(shuffledSentence)
                    WordsList(
                        originalSentList
                    ) {
                        var index = originalSentList.indexOfFirst { wrd ->
                            wrd.id == it.id
                        }

                        if (index >= 0) {
                            originalSentList[index] =
                                originalSentList[index].copy(matched = true)
                        }

                        index = shuffledSentence.indexOfFirst { wrd ->
                            wrd.id == it.id
                        }

                        if (index >= 0) {
                            shuffledSentence.remove(shuffledSentence[index])
                        }
                        if (score < originalSentList.size) {
                            score++
                            usersViewModel.addUserScore(
                                packageId,
                                packageName,
                                unScrambleScore = 1
                            )
                        }
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Tuna,
                            contentColor = Color.White
                        ),
                        onClick = {
                            val shuffled = originalSentList.shuffled()
                            for (idx in 0 until originalSentList.size) {
                                originalSentList[idx] = originalSentList[idx].copy(matched = false)
                                val shuffledWord = shuffledSentence.find{it.id == shuffled[idx].id}
                                if(shuffledWord == null) shuffledSentence.add(shuffled[idx])
                            }
                            resetTrigger++
                            score = 0
                        },
                    ) {
                        Text(
                            text = "Reset",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }


    }
    if (showDialog) {
        AlertDialog(
            onShowDialogChange = { showDialog = it },
            title = "Congratulations!",
            message = "Your score is: $score",
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShuffledWordsList(
    shuffledSentence: SnapshotStateList<Word>,
) {
    val movableItems =
        shuffledSentence.map { word ->
            movableContentOf {
                DragTarget(dataToDrop = word, modifier = Modifier.padding(6.dp)) {
                    SuggestionChip(onClick = { },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = LightSlateBlue
                        ),
                        label = {
                            Text(
                                text = word.text,
                                fontSize = 24.sp,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    )
                }
            }
        }

    FlowRow(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 25.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        shuffledSentence.forEachIndexed { index, _ ->
            movableItems[index]()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordsList(
    words: List<Word>,
    onDrop: (word: Word) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(words.size) {
            DropTarget<Word>(
                modifier = Modifier.padding(6.dp)
            ) { isInBound, wrd ->
                val bgColor = if (isInBound) {
                    Color.Yellow
                } else {
                    Color.White
                }
                if (wrd != null) {
                    if (isInBound && wrd.id == words[it].id) {
                        onDrop(words[it])
                    }
                }
                WordCard(
                    word = words[it],
                    bgColor = bgColor,
                    index = it,
                )
            }
        }
    }
}

@Composable
fun WordCard(
    word: Word, bgColor: Color, index: Int
) {
    Column(
        modifier = Modifier
            .size(90.dp, 70.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(bgColor, RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${index + 1}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        if (word.matched) {
            Text(
                text = word.text,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}