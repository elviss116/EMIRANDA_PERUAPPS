package com.emiranda.myapplication.myProfile.uploadImage.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emiranda.myapplication.R
import com.emiranda.myapplication.databinding.ModelPhotosSelectBinding
import com.emiranda.myapplication.myProfile.uploadImage.mObject.PhotoObject
import com.emiranda.myapplication.util.callback.CallbackSelectImgDelete
import java.io.File

class UploadAdapter(listPhotos: PhotoObject, callback: CallbackSelectImgDelete) : RecyclerView.Adapter<UploadAdapter.MyViewHolder>() {

    var listPhotos: PhotoObject = listPhotos
    var callback: CallbackSelectImgDelete = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.model_photos_select,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listPhotos.uri[position]
        holder.bind(data)

        holder.binding.imgDelete.setOnClickListener {
            callback.onSelectImgToDelete(position)
        }
    }

    override fun getItemCount(): Int {
        return if (listPhotos == null){
             0
        }else
            listPhotos.uri.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val binding = ModelPhotosSelectBinding.bind(itemView)

        fun bind(photo: Uri) = with(binding){
            val file = File(photo.path)
            binding.modelTxtImgSelect.text = file.name
            binding.imageView.setImageURI(photo)

        }
    }

}