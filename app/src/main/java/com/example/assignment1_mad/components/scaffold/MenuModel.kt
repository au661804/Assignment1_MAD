package com.example.assignment1_mad.components.scaffold

import androidx.compose.ui.graphics.vector.ImageVector
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class MenuItemModel(
    val id: String,
    val title: String,
    val iconVector: ImageVector,
    val contentDescription: String,
    val onClick: () -> Unit
)

data class Route(
    var id: String,
    val name: String,
    val barList: List<String>,
    val user: String
): Serializable

data class BegivenhederModel(
    // on below line creating variables.
    var id: String,
    var begivenhedName: String,
    var begivenhedDato: String,
    var begivenhedStartTime: String,
    var begivenhedEndTime: String,
    var begivenhedDescription: String,
    var begivenhedlokation: String,
    //var picture: Painter
)

data class User(val user: FirebaseUser)