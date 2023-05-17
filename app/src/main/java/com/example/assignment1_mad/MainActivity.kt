package com.example.assignment1_mad

import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
                val authenticatedState = remember { mutableStateOf(auth.currentUser?.email != null) }
                val loginService = LoginService(auth, authenticatedState)



                val menuItems = listOf(
                    //Homepage menuitem
                    if (auth.currentUser?.email != null) {
                        MenuItemModel(
                            "Forside",
                            "Forside",
                            painterResource(id = R.drawable.baseline_home_24),
                            "Forside"
                        ) {
                            Log.v("Drawer", "click Forside")
                            if (scaffoldState.drawerState.isOpen) {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                            navController.navigate("FORSIDE")
                        }
                    } else {
                        MenuItemModel(
                            "Forside",
                            "Forside",
                            painterResource(id = R.drawable.baseline_home_24),
                            "Forside"
                        ) {
                            Log.v("Drawer", "click Forside but not enabled")
                        }
                    },

                    //Tourdefredagsbar menuitem
                    if (auth.currentUser?.email != null) {
                        MenuItemModel(
                            "TDF",
                            "Tour de Fredagsbar",
                            painterResource(id = R.drawable.tdf),
                            "TDF"
                        ) {
                            Log.v("Drawer", "click TDF")
                            if (scaffoldState.drawerState.isOpen) {
                                scope.launch { scaffoldState.drawerState.open() }
                            }
                            navController.navigate("TDF")
                        }
                    } else {
                        MenuItemModel(
                            "TDF",
                            "Tour de Fredagsbar",
                            painterResource(id = R.drawable.tdf),
                            "TDF"
                        ) {
                            Log.v("Drawer", "click TDF but not enabled")
                        }
                    },

                    //Findvej menuitem
                    // if (auth.currentUser?.email != null) {
                    MenuItemModel(
                        "FindVej",
                        "Find Vej",
                        painterResource(id = R.drawable.findvej_icon),
                        "Settings"
                    ) {
                        Log.v("Drawer", "Find vej clicked")
                        if (scaffoldState.drawerState.isOpen) {
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                        navController.navigate("FINDVEJ")
                    },
//                    } else {
//                        MenuItemModel("FindVej", "Find Vej", painterResource(id = R.drawable.findvej_icon), "Settings") {
//                            Log.v("Drawer", "Find vej clicked  not but enabled")
//                        }
//                    },
                    //DineRuter menuitem
                   // if (auth.currentUser?.email != null) {
                        MenuItemModel(
                            "DineRuter",
                            "Dine Ruter",
                            painterResource(id = R.drawable.tourdefre),
                            "Settings"
                        ) {
                            Log.v("Drawer", "Dine ruter clicked")
                            if (scaffoldState.drawerState.isOpen) {
                                scope.launch { scaffoldState.drawerState.close() }
                            }
                            navController.navigate("DINERUTER")
                        }
//                    } else {
//                        MenuItemModel(
//                            "DineRuter",
//                            "Dine Ruter",
//                            painterResource(id = R.drawable.tourdefre),
//                            "Settings"
//                        ) {
//                            Log.v("Drawer", "Dine ruter clicked not but enabled")
//                        }
//                    },
                    ,

                    //Begivenheder menuitem
                  //  if (auth.currentUser?.email != null) {
                        MenuItemModel(
                            "Begivenheder",
                            "Begivenheder",
                            painterResource(id = R.drawable.begivenheder_icon),
                            "Settings"
                        ) {
                            Log.v("Drawer", "Begivenheder clicked")
                            if (scaffoldState.drawerState.isOpen) {
                                scope.launch { scaffoldState.drawerState.close() }
                            }
                            navController.navigate("BEGIVENHEDER")
                        }
//                    } else {
//                        MenuItemModel(
//                            "Begivenheder",
//                            "Begivenheder",
//                            painterResource(id = R.drawable.begivenheder_icon),
//                            "Settings"
//                        ) {
//                            Log.v("Drawer", "Begivenheder clicked but not enabled")
//                        }
//                    },
                    ,
                    MenuItemModel(
                        "LOGOUT",
                        "Log ud",
                        painterResource(id = R.drawable.logout),
                        "LOGOUT"
                    ) {
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
                    contentColor = Color.Black

                ) {
                    ScaffoldWidget(menuItems, scaffoldState,authenticatedState.value) {

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

