package com.example.assignment1_mad.components.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.assignment1_mad.*
import com.example.assignment1_mad.R

const val TAG_HOVEDMENU= "HOVEDMENU"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Hovedmenu(nav: NavController){



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Image(
                painter = painterResource(id = R.drawable.tdf_orig),
                contentDescription = "Start-upIcon",
                Modifier.size(200.dp), Alignment.Center
            )

            Spacer(modifier = Modifier.padding(bottom = 80.dp))


            TextAlign
            Text("Hvad kan du finde?", style = TextStyle(Color.Black, fontStyle = FontStyle(R.font.raleway_thin)),modifier = Modifier.background(Color.White))
            Text("-Planlæg din egen tour de fredagsbar", style = TextStyle(Color.Black))
            Text("-Se fredagsbarer og deres åbningstider", style = TextStyle(Color.Black))
            Text("-Se begivenheder fra fredagsbarer eller andres tour de fredagsbar", style = TextStyle(Color.Black))


    }}



