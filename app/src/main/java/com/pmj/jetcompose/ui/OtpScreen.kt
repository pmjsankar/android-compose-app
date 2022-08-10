package com.pmj.jetcompose.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OtpScreen(navController: NavController) {
    val context = LocalContext.current
    val otpErrorState = remember { mutableStateOf(false) }
    val otp = remember { mutableStateOf(TextFieldValue()) }
    val maxChar = 4
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 6.dp, end = 10.dp, bottom = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(modifier = Modifier.then(Modifier.size(24.dp)),
                onClick = {
                    navController.navigateUp()
                }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "OTP Verification", fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
        Spacer(Modifier.size(36.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = otp.value,
            onValueChange = {
                if (otpErrorState.value) {
                    otpErrorState.value = false
                }
                if (it.text.length <= maxChar) otp.value = it
            },
            isError = otpErrorState.value,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = "Enter the OTP*",
                    style = MaterialTheme.typography.body1
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = Color.Red
                    )
                }
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (otpErrorState.value) {
            Text(
                text = "Required", color = Color.Red,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(alignment = Alignment.Start)
            )
        }
        Spacer(Modifier.size(46.dp))
        Button(
            onClick = {
                when {
                    otp.value.text.isEmpty() -> {
                        otpErrorState.value = true
                    }
                    else -> {
                        otpErrorState.value = false
                        navController.navigate("delivery") {
                            popUpTo("otpScreen") { inclusive = true }
                        }
                        Toast.makeText(
                            context,
                            "Logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            },
            content = {
                Text(
                    text = "VERIFY", color = Color.White, fontSize = 14.sp,
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