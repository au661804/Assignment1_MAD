package com.example.assignment1_mad.components.scaffold

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment1_mad.ui.theme.Assignment1_MADTheme

const val TAG_APP_BAR = "AppBar"

@Composable
fun AppBarWidget(title: String, onNavigationIconClick: () -> Unit) {
    TopAppBar(title = { Text(title) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Toggle drawer"
                )
            }
        }
    )

}

@Preview
@Composable
fun PreviewAppBar()
{
    Assignment1_MADTheme (darkTheme = true) {
        AppBarWidget(title = "TestTitle") {
            Log.v("AppBar", "menuClicked")
        }
    }
}