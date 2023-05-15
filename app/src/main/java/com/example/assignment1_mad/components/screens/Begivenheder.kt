package com.example.assignment1_mad.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment1_mad.components.scaffold.BegivenhederModel
import com.example.assignment1_mad.services.FireStoreService

const val TAG_BEGIVENHEDER = "BEGIVENHEDER"

@Composable
fun Begivenheder(service: FireStoreService, nav: NavController) {


    val begivenheder = remember { mutableStateOf(emptyList<BegivenhederModel>()) }
    LaunchedEffect(Unit) {
        val list = service.getBegivenheder()
        begivenheder.value = list
    }
    Card() {
        Column(
            modifier = Modifier
                .background(Color.White)
        ) {
            Text(
                "Begivenheder",
                color = Color.Black,
                fontWeight = FontWeight.Light,
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(bottom = 20.dp))


        begivenheder.value.map {
            Column() {

                Row() {
                    Text("Name: ")
                    Text(it.begivenhedName)
                }
                Row() {
                    Text("Dato for begivenheder: ")
                    Text(it.begivenhedDato)
                }

                Row() {
                    Text("Fra: ${it.begivenhedStartTime} - ${it.begivenhedEndTime} ")
                    //Text(it.begivenhedStartTime )
//                    Text("Til: ")
//                    Text(it.begivenhedEndTime )
                }
                Row() {
                    Text("Hvor: ")
                    Text(it.begivenhedlokation )

                }
            }
        }
        Button(onClick = { nav.navigate("OPRETBEGIVENHED") }) {
            Text("Opret Begivenhed")
        }
    }

    }

}

