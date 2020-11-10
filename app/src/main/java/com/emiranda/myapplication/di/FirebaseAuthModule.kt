package com.emiranda.myapplication.di

import android.app.Application
import android.content.Context
import com.emiranda.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val providerFirebaseAuthModule = module {

    fun provideRequest(application: Application): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    fun providerGSC(application: Application, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(application,gso)
    }

    single { provideRequest( androidApplication() ) }
    single { providerGSC(androidApplication(),get()) }

    fun provideGoogleLoggedAccount(context: Context): GoogleSignInAccount? {
        //val account =
        return GoogleSignIn.getLastSignedInAccount(context)
    }

    single { provideGoogleLoggedAccount(androidContext()) }


    fun providerFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    single { providerFirebaseAuth() }


}
