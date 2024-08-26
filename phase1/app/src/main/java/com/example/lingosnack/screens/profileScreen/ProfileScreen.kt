package com.example.lingosnack.screens.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lingosnack.model.User
import com.example.lingosnack.ui.theme.LavendarMist
import com.example.lingosnack.ui.theme.PalePurple
import com.example.lingosnack.ui.theme.Tuna
import com.example.lingosnack.viewModel.UsersViewModel


@Composable
fun ProfileScreen(
    user: User?,
    viewModel: UsersViewModel,
    padding: PaddingValues,
    onUserLogin: (User?) -> Unit
) {
    var register by remember { mutableStateOf(false) }
    if (user == null) {
        if (!register) {
            LoginScreen(
                viewModel = viewModel,
                onUserLogin = { onUserLogin(it) }
            ) { register = it }
        } else {
            SignUpScreen(
                viewModel = viewModel,
                onClickLogin = { register = it}
            ) { onUserLogin(it) }
        }
    } else {
        Profile(user, padding,
            onSignOutClick = {
                onUserLogin(it)
                viewModel.handleSignOut()
            })
    }
}

@Composable
fun Profile(
    user: User, padding: PaddingValues,
    onSignOutClick: (User?) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(padding)
            .padding(15.dp),
        color = LavendarMist
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = user.photoUri,
                contentDescription = "profileImg",
            )
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = Tuna
            )
            Text(
                text = user.email,
                fontFamily = FontFamily.Monospace,
                fontSize = 30.sp,
                color = PalePurple
            )
            Box {
                SignOutButton(onSignOutClick = {
                    onSignOutClick(null)
                })
            }
        }
    }
}

@Composable
fun SignOutButton(onSignOutClick: (User?) -> Unit) {
    ElevatedButton(
        onClick = { onSignOutClick(null) },
        modifier = Modifier
            .padding(16.dp),

        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Tuna,
            contentColor = Color.White
        )
    )
    {
        Text(text = "Sign Out")
    }


}