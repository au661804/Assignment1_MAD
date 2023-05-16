package com.example.assignment1_mad.components.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assignment1_mad.components.screens.auth

@Composable
fun MenuItem(menuItem: MenuItemModel) {
    Row(
        Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { menuItem.onClick() }) {
        Text(
            text = menuItem.title,
            Modifier.weight(1f),
            style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onPrimary
        )
        Icon(
            imageVector = menuItem.iconVector,
            contentDescription = menuItem.contentDescription,
            tint = MaterialTheme.colors.onPrimary
        )
    }
}


