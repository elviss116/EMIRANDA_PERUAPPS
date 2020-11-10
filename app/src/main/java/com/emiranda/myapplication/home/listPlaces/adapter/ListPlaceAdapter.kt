package com.emiranda.myapplication.home.listPlaces.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emiranda.myapplication.R
import com.emiranda.myapplication.databinding.ModelListPlacesBinding
import com.emiranda.myapplication.util.callback.SelectPlaceList
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place

class ListPlaceAdapter(callback: SelectPlaceList) : RecyclerView.Adapter<ListPlaceAdapter.MyViewHolder>() {

    var callback : SelectPlaceList = callback

    private var listPlaces = mutableListOf<Place>()

    fun setDataAdapter(listPlaces : MutableList<Place>){
        this.listPlaces.clear()
        this.listPlaces.addAll(listPlaces)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPlaceAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_list_places,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListPlaceAdapter.MyViewHolder, position: Int) {

        val data = listPlaces[position]
        holder.bind(data)

        holder.binding.root.setOnClickListener {
            callback.onSelectPlace(data)
        }
    }

    override fun getItemCount(): Int {
        return if (listPlaces == null){
            0
        }else
            listPlaces.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val binding = ModelListPlacesBinding.bind(itemView)

        fun bind(place: Place) = with(binding){
            binding.txtNamePlace.text = place.detail_place
            binding.txtDate.text = place.createdAt
            Glide.with(itemView.context).load(place.images[0]).into(binding.imgPlace)

        }
    }

}