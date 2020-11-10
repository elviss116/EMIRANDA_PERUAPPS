package com.emiranda.myapplication.myProfile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.emiranda.myapplication.R
import com.emiranda.myapplication.databinding.FragmentMyAccountBinding
import com.emiranda.myapplication.login.viewModel.LoginViewModel
import com.emiranda.myapplication.util.Constants
import com.emiranda.myapplication.util.Constants.Companion.NAME_KEY_PREFS
import com.emiranda.myapplication.util.PreferencesUtil
import com.emiranda.myapplication.util.workmanager.viewmodel.GPSWorkerViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyAccountFragment : Fragment() {

    private var _binding : FragmentMyAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val prefs : PreferencesUtil by inject()
    private val viewmodel : LoginViewModel by viewModel()
    private val viewmodelTrack : GPSWorkerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragmentt)
        configView()

    }

    private fun configView(){

        binding.btnFragmentUpload.setOnClickListener {
            navController.navigate(R.id.uploadImageFragment)
        }
        viewmodel.getFirebaseAuthInstance()

        viewmodel.firebaseAuthInstanceLD.observe(viewLifecycleOwner, Observer {
            prefs.saveString(Constants.EMAIL_KEY_PREFS,it.currentUser?.email!!)
            prefs.saveString(NAME_KEY_PREFS,it.currentUser?.displayName!!)
            val captureEmail = prefs.getValueString(NAME_KEY_PREFS)
            Log.d("FRAGMENT_HOME","USER LOGGED $captureEmail")
            binding.txtUserLogged.text = it.currentUser!!.displayName
        })

        binding.btnLogout.setOnClickListener {
            viewmodel.getFirebaseAuthInstance()
            viewmodel.firebaseAuthInstanceLD.observe(viewLifecycleOwner, Observer {
                it.signOut()
                logout()
            })
        }

        checkGPSPermission()
    }

    private fun logout(){
        viewmodel.getGoogleSigInClient()
        viewmodel.gscLV.observe(viewLifecycleOwner, Observer {
            it.signOut()
            navController.navigate(R.id.loginActivity)
        })
    }

    private fun checkGPSPermission(){
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestGpsPermission()
        }else{
            startUserTracking()
        }
    }

    private fun requestGpsPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)){
            Toast.makeText(requireContext(), "PERMISO GPS DENEGADO DEFINITIVAMENTE", Toast.LENGTH_SHORT).show()
            Log.i("PERMISO", "### PERMISO DENEGADO DEFINITIVAMENTE")
        }else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),888)
        }
    }

    private fun startUserTracking(){
        viewmodelTrack.setupWorker()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 888){
            if ( grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startUserTracking()
            }else{
                Toast.makeText(requireContext(), "Permiso GPS denegado por 1ra vez", Toast.LENGTH_SHORT).show()
                Log.i("PERMISO", "### PERMISO DENEGADO")
            }

        }
    }
}