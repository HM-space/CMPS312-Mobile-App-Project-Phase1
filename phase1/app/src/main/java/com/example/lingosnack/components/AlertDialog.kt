package com.example.lingosnack.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.PalePurple
import com.example.lingosnack.ui.theme.Tuna

@Composable
fun AlertDialog(
    title: String,
    message: String,
    comment: String = "",
    rating: Int = -1,
    onCommentChange: (String) -> Unit = {},
    onRatingChange: (Int) -> Unit = {},
    onSubmit: () -> Unit = {},
    onShowDialogChange: (Boolean) -> Unit,
) {

    AlertDialog(
        containerColor = LavendarMist,
        titleContentColor = Tuna,
        textContentColor = PalePurple,
        onDismissRequest = {
            onShowDialogChange(false)
        },
        title = {
            Text(text = title)
        },
        text = {
            Column {
                Text(text = message)
                if (rating > -1) {
                    RatingBar(
                        rating = rating,
                        onRatingChanged = {
                            onRatingChange(it)
                        }
                    )
                    AppTextField(
                        value = comment,
                        label = "Add a comment (Optional)",
                        placeHolder = "Add a comment (Optional)"
                    ) {
                        onCommentChange(it)
                    }
                }
            }
        },
        confirmButton = {
        },
        dismissButton = {

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Tuna,
                    contentColor = Color.White
                ),
                enabled = if (rating > -1)
                    rating > 0
                else true,
                onClick = {
                    onSubmit()
                    onShowDialogChange(false)
                }
            ) {
                Text("Done")
            }
        }
    )

}


