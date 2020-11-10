package com.emiranda.myapplication.home.listDetailPlaces.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emiranda.myapplication.R
import com.emiranda.myapplication.databinding.ModelListDetailPlaceBinding

class DetailPlaceAdapter(listImg : MutableList<String>) : RecyclerView.Adapter<DetailPlaceAdapter.MyViewHolder>() {

    private val listImg : MutableList<String> = listImg

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_list_detail_place,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = listImg[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return if (listImg == null){
            0
        }else
            listImg.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        private val binding = ModelListDetailPlaceBinding.bind(itemView)

        fun bind(photo: String) = with(binding){
            Glide.with(itemView.context).load(photo).into(binding.imgDetail)
        }
    }
}