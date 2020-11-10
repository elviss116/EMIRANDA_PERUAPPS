package com.emiranda.myapplication.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.TaskStackBuilder
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.emiranda.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {

    var bottom : BottomNavigationView? = null
    var navController : NavController?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        bottom = findViewById(R.id.bottom_navigation)
        navController = Navigation.findNavController(this,R.id.nav_host_fragmentt)
        NavigationUI.setupWithNavController(bottom!!, navController!!);

        configBottomVisivility()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragmentt).navigateUp()
    }

    private fun configBottomVisivility() {

        navController?.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id){
                R.id.listDetailPlaceFragment -> hideBottomNav()
                R.id.uploadImageFragment -> hideBottomNav()
                else -> showBottomNav()

            }
        }
    }

    private fun hideBottomNav(){
        bottom?.visibility = View.GONE
    }
    private fun showBottomNav(){
        bottom?.visibility = View.VISIBLE
    }



}