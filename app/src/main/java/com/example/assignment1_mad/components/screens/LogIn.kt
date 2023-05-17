package com.example.assignment1_mad.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment1_mad.services.LoginService
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.launch

const val TAG_LOGIN = "LOGIN"

@Composable
fun LogIn(service: LoginService, nav: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        Text(
            text="Log in",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val errorMessage = remember { mutableStateOf("") }
        val showErrorSnackbar = remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        Button(
            onClick = {
                scope.launch {
                    try {
                        service.login(email.value.text, password.value.text)
                        //try catch
                        nav.navigate("HOVEDMENU")
                    } catch (e: Exception) {
                        when (e) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                errorMessage.value = "Invalid email or password"
                            }
                            is FirebaseAuthInvalidUserException -> {
                                errorMessage.value = "Error, something is wrong"
                            }
                            else -> {
                                errorMessage.value = "Error, something is wrong"
                            }
                        }
                        showErrorSnackbar.value = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(android.graphics.Color.parseColor("#ffa34f"))
            ),
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(7.dp)
                    .background(Color(android.graphics.Color.parseColor("#ffa34f")))
                    .padding(1.dp)
                    .fillMaxWidth(0.7f)

            ) {
                Text(
                    text = "Log in",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
        if (showErrorSnackbar.value) {
            Snackbar(
                action = {
                    Button(
                        onClick = { showErrorSnackbar.value = false },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Dismiss", color = Color.White)
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(errorMessage.value, color = Color.White)
            }
        }
    }
}