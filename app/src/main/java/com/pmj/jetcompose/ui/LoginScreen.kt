package com.pmj.jetcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoginScreen(navController: NavController) {
    val phone = remember { mutableStateOf(TextFieldValue()) }
    val phoneErrorState = remember { mutableStateOf(false) }
    val maxChar = 10
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(36.dp))
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("J")
            }
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("et")
            }

            withStyle(style = SpanStyle(color = Color.Red)) {
                append(" D")
            }
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("elivery")
            }
        }, fontSize = 30.sp)
        Spacer(Modifier.size(6.dp))
        Text(text = "Order your food online", color = Color.DarkGray)
        Spacer(Modifier.size(36.dp))
        Image(
            painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1560624052-449f5ddf0c31?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80"),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)                       // clip to the circle shape
            // add a border (optional)
        )
        Spacer(Modifier.size(36.dp))
        OutlinedTextField(
            value = phone.value,
            onValueChange = {
                if (phoneErrorState.value) {
                    phoneErrorState.value = false
                }
                if (it.text.length <= maxChar) phone.value = it
            },
            isError = phoneErrorState.value,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = "Enter your mobile number*",
                    style = MaterialTheme.typography.body1
                )
            },
        )
        if (phoneErrorState.value) {
            Text(
                text = "Required", color = Color.Red,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(alignment = Alignment.Start)
            )
        }

        Spacer(Modifier.size(36.dp))
        Button(
            onClick = {
                when {
                    phone.value.text.isEmpty() -> {
                        phoneErrorState.value = true
                    }
                    else -> {
                        phoneErrorState.value = false
                        navController.navigate("otpScreen")
                    }
                }

            },
            content = {
                Text(
                    text = "NEXT", color = Color.White, fontSize = 14.sp,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 3.dp, end = 20.dp, bottom = 3.dp
                    ),
                    style = MaterialTheme.typography.button
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        )
    }
}