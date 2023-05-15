package com.example.assignment1_mad.components.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment1_mad.services.FireStoreService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TDF(service: FireStoreService, nav: NavController) {
val scope = rememberCoroutineScope()
var selectedIndex by remember { mutableStateOf(0) }
var expanded by remember { mutableStateOf(false) }
var routename by remember { mutableStateOf("") }
var isButtonClicked by remember { mutableStateOf(false) }
val user = Firebase.auth.currentUser

val names = listOf(
    "Katrines Kælder",
    "Approximerbar",
    "Haddoks",
    "Medicinsk",
    "Fysiskfredagsbar",
    "Biologisk",
    "Alkymia"
)
val selectedBarNames = remember { mutableStateListOf<String>() }

Scaffold(topBar = {
    Text(
        text = "TOUR DE FREDAGSBAR",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(horizontal = 40.dp)
            .wrapContentHeight()

    )
}) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 16.dp)

        ) {
            Divider(color = Color.Black, thickness = 2.dp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Barrer:", style = MaterialTheme.typography.body2, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .clickable(onClick = { expanded = true })
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {
                Text(names[selectedIndex], style = MaterialTheme.typography.body1)
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Black,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    names.forEachIndexed { index, name ->
                        DropdownMenuItem(onClick = {
                            selectedIndex = index
                            expanded = false
                        }) {
                            Text(name, style = MaterialTheme.typography.body1, fontSize = 20.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    selectedBarNames.add(names[selectedIndex])
                }, modifier = Modifier.align(Alignment.CenterHorizontally)

            ) {
                Text("Add bar")
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp)
                .padding(bottom = 100.dp),


            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedBarNames) { barName ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(10.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Text(
                            text = barName,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { selectedBarNames.remove(barName) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = routename,
                    onValueChange = { routename = it },
                    label = { Text("Navngiv rute") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        scope.launch {
                            service.createBar(selectedBarNames.toList(), routename, user?.email.toString())
                            routename = ""
                            isButtonClicked = true
                            GlobalScope.launch {
                                delay(3000) // Wait for 3 seconds
                                isButtonClicked = false // Hide the message box
                            }
                        }
                    },
                    modifier = Modifier.width(100.dp),
                    enabled = routename.isNotBlank()  //når burger har skrevet i navngiv rute tekstfield, aktiveres knappen
                ) {
                    Text("Gem rute")
                }
            }
            if (isButtonClicked) {
                // Show the message box
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Din rute er gemt")
                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }
            }
        }
    }

}
}
