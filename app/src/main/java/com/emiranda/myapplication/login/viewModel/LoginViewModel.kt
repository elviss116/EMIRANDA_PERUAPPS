package com.emiranda.myapplication.login.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emiranda.myapplication.login.model.LoginDataSource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: LoginDataSource) : ViewModel() {

    private val _firebaseUserMLD = MutableLiveData<FirebaseUser>()
    val firebaseUserLD : LiveData<FirebaseUser> = _firebaseUserMLD

    private val _gscMLD = MutableLiveData<GoogleSignInClient>()
    val gscLV : LiveData<GoogleSignInClient> = _gscMLD

    private val _fireBAuthInstanceMLD = MutableLiveData<FirebaseAuth>()
    val firebaseAuthInstanceLD : LiveData<FirebaseAuth> = _fireBAuthInstanceMLD

    fun loginWithGoogle(idToken: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                _firebaseUserMLD.postValue(repository.firebaseAuthWithGoogle(idToken))
            }
        }
    }

    fun getGoogleSigInClient(){
        _gscMLD.value = repository.createRequest()
    }

    fun getFirebaseAuthInstance(){
        _fireBAuthInstanceMLD.value = repository.getFirebaseInstance()
    }
}