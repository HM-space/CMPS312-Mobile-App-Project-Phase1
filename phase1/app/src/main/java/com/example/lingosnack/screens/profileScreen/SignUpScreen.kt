package com.example.lingosnack.screens.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lingosnack.components.AppTextField
import com.example.lingosnack.model.User
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.viewModel.UsersViewModel

@Composable
fun SignUpScreen(
    viewModel: UsersViewModel,
    onClickLogin: (Boolean) -> Unit,
    onUserLogin: (User?) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf("") }

    var isValid by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf("Invalid Input") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(LavendarMist)
                .padding(8.dp)
        ) {
            item {
                Icon(
                    imageVector = Icons.Default.Cookie,
                    contentDescription = "Check Icon",
                    tint = Color(0xFFBD8C61),
                    modifier = Modifier
                        .size(60.dp)
                        .padding(top = 10.dp)
                )
                Text(
                    text = "Sign Up",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    fontSize = 24.sp,
                    color = Tuna
                )
                if (!isValid) Text(
                    text = errorMsg,
                    color = Color.Red
                )
                SignUpForm(
                    firstName,
                    lastName,
                    email,
                    password,
                    photoUri,
                    { firstName = it },
                    { lastName = it },
                    { email = it },
                    { password = it },
                    { photoUri = it },
                )
                Button(
                    onClick = {
                        if (firstName != "" &&
                            lastName != "" &&
                            email != "" &&
                            password != "" &&
                            photoUri != ""
                        ) {
                            val newUser = viewModel.handleSignUp(
                                firstName, lastName,
                                email, password, photoUri
                            )
                            newUser?.let {
                                onUserLogin(newUser)
                            }
                            if (newUser == null) {
                                isValid = false
                                errorMsg = "Email already exists."
                            }
                        } else {
                            isValid = false
                        }

                    },
                    colors = ButtonDefaults
                        .buttonColors(containerColor = Tuna),
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(text = "Sign Up")
                }
                Text(
                    text = "Login",
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            onClickLogin(false)
                        }
                        .padding(10.dp)
                )
            }
        }
    }
}


@Composable
fun SignUpForm(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    photoUri: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPhotoUriChange: (String) -> Unit,
) {
    AppTextField(
        value = firstName,
        label = "First Name",
        placeHolder = "First Name",
    ) {
        onFirstNameChange(it)
    }
    AppTextField(
        value = lastName,
        label = "Last Name",
        placeHolder = "Last Name",
    ) {
        onLastNameChange(it)
    }
    AppTextField(
        value = email,
        label = "Email",
        placeHolder = "Email",
    ) {
        onEmailChange(it)
    }
    AppTextField(
        value = password,
        label = "Password",
        placeHolder = "Password",
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    ) {
        onPasswordChange(it)
    }
    AppTextField(
        value = photoUri,
        label = "Photo URI",
        placeHolder = "Photo URI",
    ) {
        onPhotoUriChange(it)
    }
}


