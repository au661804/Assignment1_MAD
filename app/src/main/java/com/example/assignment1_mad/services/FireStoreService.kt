package com.example.assignment1_mad.services

import android.util.Log
import com.example.assignment1_mad.components.scaffold.BegivenhederModel
import com.example.assignment1_mad.components.scaffold.Route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FireStoreService(private val api: FirebaseFirestore, private val auth: FirebaseAuth) {
    companion object {
        const val TAG = "FIRE_STORE_SERVICE"
    }


    //Begivenheder service
    suspend fun getBegivenheder(): List<BegivenhederModel> {
        return suspendCoroutine { continuation ->
            api.collection("Begivenheder")
                .get()
                .addOnSuccessListener {
                    val begivenheder =
                        it.documents.map { d -> BegivenhederModel(
                            d.data?.get("User").toString(),
                            d.data?.get("Name").toString(),
                            d.data?.get("Dato").toString(),
                            d.data?.get("Starttidspunkt").toString(),
                            d.data?.get("Sluttidspunkt").toString(),
                            d.data?.get("Beskrivelse").toString(),
                            d.data?.get("Lokation").toString(),) }
                    continuation.resume(begivenheder)
                }.addOnFailureListener {
                    Log.v(TAG, "We failed $it")
                    throw it
                }
        }
    }

    suspend fun createBegivenhed(
        id: String,
        name: String,
        date: String,
        starttime:String,
        endTime: String,
        begivenhedBeskrivelse: String,
        lokation:String ) {

        val begivenhed = hashMapOf(
            "User" to id,
            "Name" to name,
            "Dato" to date,
            "Starttidspunkt" to starttime,
            "Sluttidspunkt" to endTime,
            "Beskrivelse" to begivenhedBeskrivelse,
            "Lokation" to lokation)
        suspendCoroutine { continuation ->
            api.collection("Begivenheder")
                .add(begivenhed)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    continuation.resume(documentReference.id)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    throw e
                }
        }
    }



    //TDF Service
    suspend fun loadRouteFromFirebase(): List<Route> {
        return suspendCoroutine { continuation ->
            api.collection("turDeFredagsbarRute")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val routes =querySnapshot.documents.map { documentSnapshot ->
                        val routeName = documentSnapshot.data?.get("RouteName").toString()
                        val barList = documentSnapshot.data?.get("barList") as? List<String> ?: emptyList()
                        val user = documentSnapshot.data?.get("User").toString()
                        Route(documentSnapshot.id, routeName, barList, user)
                    }
                    continuation.resume(routes)
                }.addOnFailureListener { exception ->
                    Log.v(TAG, "We failed $exception")
                    throw exception
                }
        }
    }


    suspend fun createBar(name: List<String>,routename: String, user: String) {
        val bar = hashMapOf("barList" to name, "RouteName" to routename, "User" to user)

        suspendCoroutine { continuation ->
            api.collection("turDeFredagsbarRute")
                .add(bar)

                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    continuation.resume(documentReference.id)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    throw e
                }
        }
    }
}