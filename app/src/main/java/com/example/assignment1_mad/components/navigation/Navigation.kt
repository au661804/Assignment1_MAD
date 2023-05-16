package com.example.assignment1_mad.components.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.assignment1_mad.components.screens.*
import com.example.assignment1_mad.services.FireStoreService
import com.example.assignment1_mad.services.LoginService
import com.example.assignment1_mad.services.Service
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Navigation(
    navController:NavHostController,
    locationService: Service,
    firestoreService: FireStoreService,
    loginService:LoginService) {
    val auth = Firebase.auth
    NavHost(navController = navController, startDestination = "LOGINSIGNUPSTART") {
        composable("LOGINSIGNUPSTART"){
            LoginSignUp_startwindow(navigateSignUp={navController.navigate("SIGNUP")},
                navigateLogIn = {navController.navigate("LOGIN")},service=loginService)

        }
        composable("FINDVEJ") {
            FindVej(locationService)
        }
        composable("TDF") {
            TDF(firestoreService,navController)
        }
        composable("DINERUTER") {
            DineRuter(firestoreService,navController)
        }
        composable("OPRETBEGIVENHED") {
            OpretBegivenhed(firestoreService,navController)
        }
        composable("BEGIVENHEDER") {
            Begivenheder(firestoreService,navController)
        }
        composable("SIGNUP") {
            SignUp(service = loginService, nav = navController)
        }
        composable("LOGIN") {
           LogIn(service = loginService, nav = navController)
        }
        composable("HOVEDMENU") {
            Hovedmenu(nav = navController)
        }
    }
}

