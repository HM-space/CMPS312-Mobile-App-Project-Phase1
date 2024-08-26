package com.example.lingosnack.screens.games.flashCards


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lingosnack.components.VideoPlayer
import com.example.lingosnack.model.Sentence
import com.example.lingosnack.model.Word
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.PalePurple
import com.example.lingosnack.ui.theme.Tuna


@Composable
fun FlashCardsGame(
    word: Word,
    padding: PaddingValues
) {
    Card(
        modifier = Modifier
            .padding(padding)
            .padding(top = 8.dp)
            .padding(horizontal = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = LavendarMist
        )
    )
    {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
        ) {

            Text(
                word.text,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                color = Tuna
            )
            Divider(color = PalePurple, thickness = 2.dp)
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(
                    "Definitions:",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    color = Tuna
                )
                word.definitions.forEach {
                    Text(
                        it.text,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = Tuna
                    )
                }
                Text(
                    "Sentences:",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    color = Tuna
                )
                word.sentences.forEach { sentence ->
                    SentenceSection(sentence)
                }
            }
        }
    }
}

@Composable
fun SentenceSection(sentence: Sentence) {
    val uriHandler = LocalUriHandler.current
    var showImg by remember { mutableStateOf(false) }
    var showVideo by remember { mutableStateOf(false) }
    Text(
        sentence.text,
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        color = Tuna
    )

    val linkResources = sentence.resources
        .filter { it.type == "Website" }
    val imageResources = sentence.resources
        .filter { it.type == "Photo" }
    val videoResources = sentence.resources
        .filter { it.type == "Video" }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (linkResources.isNotEmpty()) {
            linkResources.forEach {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Tuna,
                        contentColor = Color.White
                    ),
                    onClick = {
                        uriHandler.openUri(it.url)
                    }
                ) {
                    Text(text = "Open Link")
                }
            }
        }

        if (imageResources.isNotEmpty()) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Tuna,
                    contentColor = Color.White
                ),
                onClick = {
                    showImg = !showImg
                }
            ) {
                Text(
                    text =
                    if (showImg) "Hide Images"
                    else "Show Images"
                )
            }
        }
        if (videoResources.isNotEmpty()) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Tuna,
                    contentColor = Color.White
                ),
                onClick = {
                    showVideo = !showVideo
                }
            ) {
                Text(
                    text =
                    if (showVideo) "Hide Video"
                    else "Show Video"
                )
            }
        }
    }

    Column {
        if (showImg) {
            for (resource in imageResources) {
                AsyncImage(
                    model = resource.url,
                    contentDescription = "image",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (showVideo) {
            VideoPlayer(uri = videoResources.first().url)
        }
    }
}