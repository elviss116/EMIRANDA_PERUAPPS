package com.emiranda.myapplication.home.listDetailPlaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.emiranda.myapplication.R
import com.emiranda.myapplication.databinding.FragmentListDetailPlaceBinding
import com.emiranda.myapplication.home.listDetailPlaces.adapter.DetailPlaceAdapter


class ListDetailPlaceFragment : Fragment() {

    private lateinit var _binding : FragmentListDetailPlaceBinding
    private val binding get() = _binding
    private var adapter = DetailPlaceAdapter(arrayListOf())
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListDetailPlaceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragmentt)

        val array = arguments?.get("array_img") as MutableList<String>
        binding.rvDetailPlace.layoutManager = LinearLayoutManager(requireContext())
        adapter = DetailPlaceAdapter(array)
        binding.rvDetailPlace.adapter = adapter

        println("## DETAIL PLACES ##")
        println(array)

        (activity as AppCompatActivity?)?.setSupportActionBar(binding.mainToolbarDetail)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.mainToolbarDetail.title = "Detalle"
        setHasOptionsMenu(true)
    }

}