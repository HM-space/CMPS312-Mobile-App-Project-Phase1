package com.example.lingosnack.screens.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingosnack.components.AlertDialog
import com.example.lingosnack.model.LearningPackage
import com.example.lingosnack.model.Rating
import com.example.lingosnack.model.User
import com.example.lingosnack.ui.theme.NeonPurple
import com.example.lingosnack.ui.theme.Orchid
import com.example.lingosnack.ui.theme.PaleLavender
import com.example.lingosnack.ui.theme.RipePlum
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PackageCard(learningPackage: LearningPackage, user: User?,
                onSelectPackage: (Int) -> Unit) {

    var showDialog by remember {
        mutableStateOf(false)
    }
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    val avgRating = learningPackage.getAverageRating()
    val avgRatingText = if (avgRating > 0.0) String.format("%.1f %s",avgRating,"/ 5.0") else "N/A"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onSelectPackage(learningPackage.packageId)
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
        border = BorderStroke(2.dp, Orchid),
        colors = CardDefaults.cardColors(
            containerColor = PaleLavender,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = learningPackage.title,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                fontFamily = FontFamily.Serif,
                color = RipePlum
            )
            Row {
                Text(
                    text = "Category: ",
                    color = NeonPurple,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = learningPackage.category,
                    color = NeonPurple,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row {
                Text(
                    text = "Level: ",
                    color = NeonPurple,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = learningPackage.level,
                    color = NeonPurple,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Row (
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(
                        text = "Rating: ",
                        color = NeonPurple,
                        fontStyle = FontStyle.Italic
                    )
                    Text(
                        text = avgRatingText,
                        color = NeonPurple,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                if(user != null) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Text(
                            text = "Rate me",
                            fontSize = 18.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            color = RipePlum
                        )
                        IconButton(
                            onClick = { showDialog = true },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = RipePlum
                            )
                        }
                    }
                }
            }
        }
    }
    if (showDialog)
        AlertDialog(title = "Rate", message = "Rate this learning package",
            comment, rating,
            onRatingChange = { rating = it },
            onCommentChange = { comment = it },
            onSubmit = {
                val today = LocalDateTime.now()
                val dateTime = kotlinx.datetime.LocalDateTime(
                    today.year,today.month,today.dayOfMonth,
                    today.hour,today.minute,today.second,today.nano
                )
                learningPackage.ratings.add(Rating(comment, dateTime, user?.email ?: "",rating))
            }
        ) {
            showDialog = it
        }
}