package com.example.assignment1_mad.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment1_mad.components.scaffold.Route
import com.example.assignment1_mad.services.FireStoreService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

const val TAG_DINERUTER = "DINERUTER"

@Composable
fun DineRuter(service: FireStoreService, nav: NavController) {
    val Route = remember { mutableStateOf(emptyList<Route>()) }
    val user = Firebase.auth.currentUser //logged in user

    LaunchedEffect(Unit) {
        val list = service.loadRouteFromFirebase().filter { it.user == user?.email }
        Route.value = list

    }

    Column(
        modifier = Modifier

            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "DINE RUTER",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        val dropdownStates = remember { mutableStateMapOf<Route, Boolean>() }

        Route.value.forEach { route ->
            Column {
                Button(onClick = {
                    dropdownStates[route] = dropdownStates.getOrDefault(route, false).not()
                }, modifier = Modifier.fillMaxWidth(), content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Route: ${route.name}", fontSize = 35.sp, color = Color.Black)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Arrow",
                        )
                    }
                })
                PaddingValues(20.dp)
                if (dropdownStates[route] == true) {
                    val bars = route.barList
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { dropdownStates[route] = false },
                    ) {
                        bars.forEach { bar ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        dropdownStates[route] = false
                                    },
                                    modifier=Modifier.fillMaxWidth()
                                ) {
                                    Text(bar, fontSize = 30.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}