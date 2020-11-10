package com.emiranda.myapplication.login.model

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginRepository(private val auth: FirebaseAuth, private val gsc: GoogleSignInClient) : LoginDataSource{

    private var user: FirebaseUser? = null

    override suspend fun firebaseAuthWithGoogle(idToken: String): FirebaseUser? {

        withContext(Dispatchers.IO){
            try {
                val credential = GoogleAuthProvider.getCredential(idToken,null)
                auth.signInWithCredential(credential).await()
                user = auth.currentUser
            }catch (e: Exception){
                Log.d("REPO_LOGIN",e.localizedMessage)
            }
        }
        return user
    }

    override fun createRequest(): GoogleSignInClient {
        return gsc
    }

    override fun getFirebaseInstance(): FirebaseAuth {
        return auth
    }

}