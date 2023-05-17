package com.example.assignment1_mad.services

import android.util.Log
import androidx.compose.runtime.MutableState
import com.example.assignment1_mad.components.scaffold.User
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine



class LoginService(private val auth: FirebaseAuth, private val authenticatedState: MutableState<Boolean>) {
    companion object {
        const val TAG = "FIRE_STORE_SERVICE"
    }



    suspend fun login(email: String, password: String): User {
        return suspendCoroutine { continuation ->

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            Log.d(TAG, "Logged in :success")
                            val user = auth.currentUser ?: throw Exception("Something wrong")
                            val SignedInUser = User(user)
                            authenticatedState.value = true
                            continuation.resume(SignedInUser)
                        } else {
                            Log.w(TAG, "Logged in:failure", task.exception)
                            throw Exception("login: $email failure", task.exception)
                        }
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }
        }
    }

    suspend fun signup(email: String, password: String): User {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser ?: throw Exception("Something wrong")
                            val signedInUser = User(user)
                            continuation.resume(signedInUser)

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            throw Exception("createUserWithEmail: $email failure", task.exception)
                        }
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }
        }
    }
    fun logOut(auth: FirebaseAuth) {
        auth.signOut()
        authenticatedState.value = false
    }
}

