package com.emiranda.myapplication.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.emiranda.myapplication.R
import com.emiranda.myapplication.databinding.FragmentHomeBinding
import com.emiranda.myapplication.util.callback.SelectPlaceList
import com.emiranda.myapplication.home.listPlaces.adapter.ListPlaceAdapter
import com.emiranda.myapplication.home.listPlaces.viewmodel.ListPlaceRoomViewModel
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SelectPlaceList {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    private val listRoomPlaceViewModel : ListPlaceRoomViewModel by viewModel()
    private var madapter = ListPlaceAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragmentt)
        configView()
        configObserver()
    }

    private fun configView(){
        binding.rvPlaces.layoutManager = GridLayoutManager(requireContext(),2)
    }

    private fun configObserver(){

        listRoomPlaceViewModel.getRoomData()
        listRoomPlaceViewModel.places.observe(viewLifecycleOwner, Observer {
            println("### LISTA ROOM ###")
            println(it)
            madapter.setDataAdapter(it)
            binding.rvPlaces.adapter = madapter
            madapter.notifyDataSetChanged()
        })

        listRoomPlaceViewModel.emptyList.observe(viewLifecycleOwner, Observer {
            if (it<=0){
                binding.imgEmpty.visibility = View.VISIBLE
            }else{
                binding.imgEmpty.visibility = View.GONE
            }
        })
    }

    override fun onSelectPlace(place: Place) {
        val bundle = bundleOf("array_img" to place.images)
        navController.navigate(R.id.listDetailPlaceFragment,bundle)
    }
}