package com.example.assignment1_mad.components.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment1_mad.R
import com.example.assignment1_mad.services.LoginService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

const val TAG_LOGINSIGNUPSTART = "LOGINSIGNUPSTART"

val auth = Firebase.auth
@Composable
fun LoginSignUp_startwindow(navigateSignUp: () -> Unit, navigateLogIn: () -> Unit, service:LoginService) {
service.logOut(auth) //Logger bruger ud

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(Color.White),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))
        
        Image(
            painterResource(id = R.drawable.tdf_orig),
            contentDescription = "App Icon",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))

        )

        Spacer(modifier = Modifier.height(100.dp))

        Button(
            onClick = {
                navigateLogIn()
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
                    .height(25.dp)

            ) {
                Text(
                    text = "Login",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Button(
            onClick = {
                navigateSignUp()
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
                    .height(25.dp)

            ) {
                Text(
                    text = "Sign Up",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}