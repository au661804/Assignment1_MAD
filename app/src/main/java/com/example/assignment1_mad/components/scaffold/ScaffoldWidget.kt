package com.example.assignment1_mad.components.scaffold

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

const val TAG_SCAFFOLD = "Scaffold"





@SuppressLint("SuspiciousIndentation")
@Composable
fun ScaffoldWidget(menuItems: List<MenuItemModel>, scaffoldState: ScaffoldState, loggedIn:Boolean, content: @Composable (PaddingValues) -> Unit) {
    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState, topBar = {
        AppBarWidget(title = "TOUR") {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }
    }, drawerContent = {
        Log.d("Scaffold", "Drawer opened with ${loggedIn}")
        Drawer("Fredagsbar", menuItems,loggedIn)
    }) {
        content(it)
    }
}

