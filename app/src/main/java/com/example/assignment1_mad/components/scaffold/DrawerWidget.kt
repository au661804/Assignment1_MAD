package com.example.assignment1_mad.components.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Drawer(title: String, menuItems: List<MenuItemModel>) {
    Column(
        modifier = Modifier.background(Color.White)
    ) {

        DrawerHeader(title)
        DrawerBody(menuItems)

        Spacer(modifier = Modifier.height(150.dp))


    }
}

@Composable
fun DrawerHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp)
            .background(color = Color.White)
//            .fillMaxWidth()
//            .padding(44.dp), contentAlignment = Alignment.Center

    ) {
        Text(title, color = Color.Black, style = MaterialTheme.typography.h4)
    }
}

@Composable
fun DrawerBody(menuItems: List<MenuItemModel>) {
    LazyColumn(Modifier.background(color = Color.White)) {
        items(menuItems) { item ->
            MenuItem(menuItem = item)
        }
    }
}


