package com.emiranda.myapplication.login.model

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface LoginDataSource {

    suspend fun firebaseAuthWithGoogle(idToken : String) : FirebaseUser?

    fun createRequest() : GoogleSignInClient

    fun getFirebaseInstance(): FirebaseAuth
}