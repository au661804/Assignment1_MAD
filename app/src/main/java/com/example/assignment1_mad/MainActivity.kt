package com.example.assignment1_mad

import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.assignment1_mad.components.navigation.Navigation
import com.example.assignment1_mad.components.scaffold.MenuItemModel
import com.example.assignment1_mad.components.scaffold.ScaffoldWidget
import com.example.assignment1_mad.services.FireStoreService
import com.example.assignment1_mad.services.LoginService
import com.example.assignment1_mad.services.Service
import com.example.assignment1_mad.ui.theme.Assignment1_MADTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geoCoder: Geocoder
    private lateinit var locationService: Service


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Authentication
        val auth = Firebase.auth
        FirebaseApp.initializeApp(this)
        val loginService = LoginService(auth)
        Firebase.firestore.app
        val db = FirebaseFirestore.getInstance()
        val firestoreService = FireStoreService(db, auth)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geoCoder = Geocoder(this, Locale.getDefault())
        locationService = Service(fusedLocationClient, this, geoCoder)

        setContent {
            Assignment1_MADTheme() {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()


                val menuItems = listOf(
                    //Tourdefredagsbar menuitem
                    if (auth.currentUser?.email != null) {
                        MenuItemModel("TDF", "Tour de Fredagsbar", Icons.Default.Home, "TDF") {
                            Log.v("Drawer", "click TDF")
                            if (scaffoldState.drawerState.isOpen) {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                            navController.navigate("TDF")
                        }
                    } else {
                        MenuItemModel("TDF", "Tour de Fredagsbar", Icons.Default.Lock, "TDF") {
                            Log.v("Drawer", "click TDF but enabled")
                        }
                    },

                    //Findvej menuitem
                    if (auth.currentUser?.email != null) {
                    MenuItemModel("FindVej", "Find Vej", Icons.Default.Settings, "Settings") {
                        Log.v("Drawer", "Find vej clicked")
                        if (scaffoldState.drawerState.isOpen) {
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                        navController.navigate("FINDVEJ")
                    }
                    } else {
                        MenuItemModel("FindVej", "Find Vej", Icons.Default.Settings, "Settings") {
                            Log.v("Drawer", "Find vej clicked but enabled")
                        }
                    },

                    //DineRuter menuitem
                    if (auth.currentUser?.email != null) {
                        MenuItemModel(
                            "DineRuter", "Dine Ruter", Icons.Default.Settings, "Settings"
                        ) {
                            Log.v("Drawer", "Dine ruter clicked")
                            if (scaffoldState.drawerState.isOpen) {
                                scope.launch { scaffoldState.drawerState.close() }
                            }
                            navController.navigate("DINERUTER")
                        }
                    } else {
                        MenuItemModel(
                            "DineRuter", "Dine Ruter", Icons.Default.Lock, "Settings"
                        ) {
                            Log.v("Drawer", "Dine ruter clicked but enabled")
                        }
                    },

                    //Opretbegivenhed menuitem
                    if (auth.currentUser?.email != null) {
                        MenuItemModel(
                            "Opretbegivenhed",
                            "Opret begivenhed",
                            Icons.Default.Settings,
                            "Settings"
                        ) {
                            Log.v("Drawer", "Opret Begivenhed clicked")
                            if (scaffoldState.drawerState.isOpen) {
                                scope.launch { scaffoldState.drawerState.close() }
                            }
                            navController.navigate("OPRETBEGIVENHED")
                        }
                    } else {
                        MenuItemModel(
                            "Opretbegivenhed",
                            "Opret begivenhed",
                            Icons.Default.Lock,
                            "Settings"
                        ) {
                            Log.v("Drawer", "Opret Begivenhed clicked but enabled")
                        }
                    },

                    //Begivenheder menuitem
                    MenuItemModel(
                        "Begivenheder", "Begivenheder", Icons.Default.Settings, "Settings"
                    ) {
                        Log.v("Drawer", "Begivenheder clicked")
                        if (scaffoldState.drawerState.isOpen) {
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                        navController.navigate("BEGIVENHEDER")
                    },


                    MenuItemModel("LOGOUT", "Log ud", Icons.Default.Home, "LOGOUT") {
                        Log.v("Drawer", "click LogOut")
                        if (scaffoldState.drawerState.isOpen) {
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                        navController.navigate("LOGINSIGNUPSTART")

                    },
                )


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White,

                    ) {
                    ScaffoldWidget(menuItems, scaffoldState) {
                        Navigation(
                            navController, locationService, firestoreService, loginService
                        )
                    }
                }

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        Log.v("MAINACTIVITY", permissions.toString())
        when (requestCode) {
            Service.REQUEST_ID -> {
                // Check if the permission was granted or denied
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v("MAINACTIVITY", "Permission was granted")// Permission was granted
                    locationService.setLocationOn()
                } else {
                    Log.v("MAINACTIVITY", "Permission was denied")
                    locationService.setLocationOff()
                    // Disable location-related functionality
                }
                return
            }
            // Handle other permission requests if needed
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}

