package com.example.assignment1_mad.components.scaffold

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuItem(menuItem: MenuItemModel, loggedIn: Boolean) {
    val noAction: ()-> Unit = {};
    Row(
        Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                if(loggedIn)
                {menuItem.onClick()  }
                else{
                    noAction
                }

                 }) {
        Icon(
            painter = menuItem.image,
            contentDescription = menuItem.contentDescription,
            tint = Color.Black,
            modifier = Modifier.size(24.dp,24.dp),

        )

        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = menuItem.title,
            Modifier.weight(1f),
            style = MaterialTheme.typography.h5,
            color = Color.Black,
            fontSize = 16.sp
        )

    }

    Spacer(modifier = Modifier.width(20.dp))

    Divider(
        color = Color(0xFFE9E5DE),
        modifier = Modifier.width(320.dp)
            )
}


