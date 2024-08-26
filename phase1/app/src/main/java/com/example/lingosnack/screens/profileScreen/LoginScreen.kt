package com.example.lingosnack.screens.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
fun LoginScreen(
    viewModel: UsersViewModel,
    onUserLogin: (User) -> Unit,
    onSignUp: (Boolean) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(LavendarMist)

        ) {
            Icon(
                imageVector = Icons.Default.Cookie,
                contentDescription = "Check Icon",
                tint = Color(0xFFBD8C61),
                modifier = Modifier
                    .size(60.dp)
                    .padding(top = 10.dp)
            )
            Text(
                text = "LingoSnacks",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                color = Tuna
            )
            if (!isValid) {
                Text(text = "Incorrect username or password", color = Color.Red)
            }
            LoginForm(
                email,
                password,
                { email = it },
                { password = it },
            )
            LoginButtons(email, password, viewModel,
                onUserLogin = { onUserLogin(it) },
                onSignUp = { onSignUp(true) }
            ) { isValid = it }
        }
    }
}


@Composable
fun LoginForm(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
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
}

@Composable
fun LoginButtons(
    email: String,
    password: String,
    viewModel: UsersViewModel,
    onUserLogin: (User) -> Unit,
    onSignUp: (Boolean) -> Unit,
    validation: (Boolean) -> Unit
) {
    ElevatedButton(
        onClick = {
            val res = viewModel.handleLogin(email, password)
            if (res != null) {
                onUserLogin(res)
            } else {
                validation(false)
            }
        },
        modifier = Modifier
            .padding(top = 5.dp, end = 5.dp, bottom = 5.dp),
        colors = ButtonDefaults
            .buttonColors(containerColor = Tuna )
    ) {
        Text(text = "Login")
    }
    Text(
        text = "Sign Up",
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .clickable {
                onSignUp(true)
            }
            .padding(10.dp)
    )
}
