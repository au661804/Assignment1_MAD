package com.example.assignment1_mad.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment1_mad.R
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
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Text(
                    "Begivenheder",
                    color = Color.Black,
                    fontWeight = FontWeight.Light,
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center
                )
            }

            Divider(
                color = Color(0xFFE9E5DE),
                modifier = Modifier.width(320.dp)
            )

            Row() {
                Button(modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = Color(android.graphics.Color.parseColor("#ffa34f"))
                    ),
                    shape = RoundedCornerShape(percent = 50),
                    onClick = { nav.navigate("OPRETBEGIVENHED") }) {
                    Text("Opret Begivenhed")
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(modifier = Modifier.padding(bottom = 20.dp))

                begivenheder.value.map {


                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .wrapContentSize()
                            .width(350.dp),
                        color = Color(0xFFE6DDD6)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier
                                .wrapContentSize()
                                .width(350.dp)
                                .height(30.dp),
                            color = Color(0xFF61C0D8)
                        ) {
                            Row() {
                                Text(
                                    text = it.begivenhedlokation,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                                )
                            }

                        }
                        Spacer(modifier = Modifier.padding(bottom = 50.dp))

                        Column() {

                            Spacer(modifier = Modifier.padding(bottom = 40.dp))



                            Row() {
                                Text(it.begivenhedName,
                                    fontStyle = FontStyle.Italic,
                                color = Color.Black)
                            }

                            Row() {
                                Text("Dato: ${it.begivenhedDato} ",color = Color.Black)

                            }

                            Row() {
                                Text("Fra: ${it.begivenhedStartTime} - ${it.begivenhedEndTime} ",
                                    color = Color.Black)

                            }

                            OutlinedButton(
                                modifier = Modifier.padding(5.dp, 5.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color(android.graphics.Color.parseColor("#ffa34f"))
                                ),
                                onClick = { /*TODO*/ }
                            ) {
                                Text(
                                    text = "Se mere",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(bottom = 10.dp))

                }
            }


        }


    }


}




