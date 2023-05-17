package com.example.assignment1_mad.components.scaffold

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun Drawer(title: String, menuItems: List<MenuItemModel>, loggedIn: Boolean) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {

            DrawerHeader(title)
            DrawerBody(menuItems, loggedIn)

            Spacer(modifier = Modifier.height(150.dp))


        }
    }


@Composable
fun DrawerHeader( title: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp)
            .background(color = Color.White),
//            .fillMaxWidth()
//            .padding(44.dp),
            contentAlignment = Alignment.Center

    )

    {


        Text(title,
            color = Color.Black,
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center)
    }
}

@Composable
fun DrawerBody(menuItems: List<MenuItemModel>, loggedIn: Boolean) {

    LazyColumn(Modifier.background(color = Color.White)) {
        items(menuItems) { item ->
            MenuItem(menuItem = item, loggedIn)
        }
    }
}


