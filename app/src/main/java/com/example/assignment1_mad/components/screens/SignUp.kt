package com.example.assignment1_mad.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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

const val TAG_SIGNUP = "SIGNUP"

@Composable
fun SignUp(service: LoginService, nav: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

    ) {
        val email = remember { mutableStateOf(TextFieldValue("")) }
        val password = remember { mutableStateOf(TextFieldValue("")) }
        val scope = rememberCoroutineScope()

        Text(
            text = "Sign Up",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(text = "Email: ") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        Button(
            onClick = {
                scope.launch {
                    try {
                        val user = service.signup(email.value.text, password.value.text)
                        nav.navigate("LOGIN")
                    } catch (e: Exception) {
                        when (e) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                // skrive noget når email eller password er forkert
                            }
                            is FirebaseAuthInvalidUserException -> {
                                // når user is not found eller andet.
                            }
                            else -> {
                                // alt andet
                            }
                        }
                    }
                }
            }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(android.graphics.Color.parseColor("#ffa34f"))
            ), modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(7.dp)
                    .background(Color(android.graphics.Color.parseColor("#ffa34f")))
                    .padding(1.dp)
                    .fillMaxWidth(0.7f)

            ) {
                Text(
                    text = "Sign Up",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
        Button(
            onClick = {
                nav.navigate("LogIn")
            }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(
                    android.graphics.Color.parseColor("#FFFFFF")
                )
            ), modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(7.dp)
                    .background(Color.Transparent)
                    .padding(1.dp)
                    .fillMaxWidth(0.7f)
                    .height(15.dp)//try

            ) {
                Text(
                    text = "Already a user? try Login",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }

    }
}