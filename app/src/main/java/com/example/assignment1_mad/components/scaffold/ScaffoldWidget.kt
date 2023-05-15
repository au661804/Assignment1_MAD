package com.example.assignment1_mad.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.example.assignment1_mad.R
import kotlinx.coroutines.launch

const val TAG_SCAFFOLD = "Scaffold"


@Composable
fun ScaffoldWidget(menuItems: List<MenuItemModel>, scaffoldState: ScaffoldState, content: @Composable (PaddingValues) -> Unit) {
    val scope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState, topBar = {
        AppBarWidget(title = "TOUR") {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }
    }, drawerContent = {
        Drawer("Fredagsbar", menuItems)
    }) {
        content(it)
    }


}