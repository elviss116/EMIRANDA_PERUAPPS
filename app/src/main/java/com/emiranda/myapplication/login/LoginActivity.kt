package com.emiranda.myapplication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.emiranda.myapplication.MainActivity
import com.emiranda.myapplication.R
import com.emiranda.myapplication.databinding.ActivityLoginBinding
import com.emiranda.myapplication.login.viewModel.LoginViewModel
import com.emiranda.myapplication.menu.MenuActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    val TAG = "LOGINMVVM GOOGLE"
    val RC_SIGN_IN = 100

    private val viewmodel : LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configView()
    }

    private fun configView(){

        binding.btnLogin.setOnClickListener {
            sigIn()
        }
    }

    override fun onStart() {
        super.onStart()

        viewmodel.getFirebaseAuthInstance()
        viewmodel.firebaseAuthInstanceLD.observe(this, Observer {
            if (it.currentUser!=null){
                val i = Intent(this,MenuActivity::class.java)
                startActivity(i)
            }
        })
    }

    private fun sigIn(){
        viewmodel.getGoogleSigInClient()
        viewmodel.gscLV.observe(this, Observer {
            startActivityForResult(it.signInIntent,RC_SIGN_IN)
        })

    }

    private fun firebaseAuthWithGoogle(idToken: String){

        viewmodel.loginWithGoogle(idToken)
        viewmodel.firebaseUserLD.observe(this, Observer {
            val name = it.displayName
            val email = it.email
            })
            //INTENT AL MENU
            val i = Intent(this,MenuActivity::class.java)
            startActivity(i)
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            }catch (e: ApiException){
                Log.d(TAG,"Google Sign in failder ${e.message}")
            }
        }
    }

    override fun onBackPressed() {

    }

}