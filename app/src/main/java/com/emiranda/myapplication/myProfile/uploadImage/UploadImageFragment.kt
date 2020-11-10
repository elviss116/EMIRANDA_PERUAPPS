package com.emiranda.myapplication.myProfile.uploadImage

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emiranda.myapplication.databinding.FragmentUploadImageBinding
import com.emiranda.myapplication.myProfile.uploadImage.adapter.UploadAdapter
import com.emiranda.myapplication.myProfile.uploadImage.mObject.PhotoObject
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import com.emiranda.myapplication.myProfile.uploadImage.viewmodel.SavePlaceViewModel
import com.emiranda.myapplication.myProfile.uploadImage.viewmodel.UploadImageViewModel
import com.emiranda.myapplication.util.callback.CallbackSelectImgDelete
import kotlinx.android.synthetic.main.fragment_upload_image.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UploadImageFragment : Fragment(), CallbackSelectImgDelete {

    private var _binding : FragmentUploadImageBinding? = null
    private val binding get() = _binding!!
    private val REQUEST_CODE_IMG_PICK = 0

    private var curFileList : MutableList<Uri> = arrayListOf()
    private var imageNameList : MutableList<String> = arrayListOf()

    private lateinit var photoObject: PhotoObject

    private lateinit var madapter: UploadAdapter

    private val viewmodel : UploadImageViewModel by viewModel()
    private val viewmodelSave : SavePlaceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUploadImageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configObservers()
        configView()

        viewmodel.listUri.observe(viewLifecycleOwner, Observer { listUrlUploaded ->

            val currentDate: String = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            val place = Place(UUID.randomUUID().toString(),binding.txtDescrip.text.toString(),listUrlUploaded,currentDate)
            viewmodelSave.savePlace(place)
        })

    }

    private fun configView(){

        binding.rvImg.layoutManager = LinearLayoutManager(requireContext())

        binding.btnSelect.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                it.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                startActivityForResult(it, REQUEST_CODE_IMG_PICK)
            }
        }

        binding.btnUpload.setOnClickListener {
            uploadImageViewModel()
        }

        (activity as AppCompatActivity?)?.setSupportActionBar(binding.mainToolbarUpload)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.mainToolbarUpload.title = "Compartir Lugar"
        setHasOptionsMenu(true)

    }

    private fun configObservers() {
        viewmodel.success.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewmodel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewmodelSave.msgWhenSaveLD.observe(viewLifecycleOwner, Observer {
            viewmodel._saveProccess.value = false
            clearData()
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
        })

        viewmodel.saveInProccess.observe(viewLifecycleOwner, Observer {
            if (it){
                binding.progress.visibility = View.VISIBLE
                Toast.makeText(requireContext(),"Compartiendo Datos...",Toast.LENGTH_SHORT).show()
            }else{
                binding.progress.visibility = View.GONE
            }
        })
    }

    private fun clearData(){
        binding.txtDescrip.setText("")
        curFileList.clear()
        madapter.notifyDataSetChanged()
    }

    private fun uploadImageViewModel(){
        try {
            curFileList?.let {
                photoObject = PhotoObject(it)
                viewmodel.uploadImage(photoObject,binding.txtDescrip.text.toString())
            }
        }catch (e: Exception){
            Log.d("UPLOAD_FRAGMENT: ",e.localizedMessage)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMG_PICK){

            curFileList.clear()
            if (data?.clipData != null) {
                var count = (data.clipData!!.itemCount) -1
                println("Total : $count")
                for ( i in 0..1){//count
                    val uri = data?.clipData!!.getItemAt(i).uri
                    var file = File(uri.path)
                    val name = file.name
                    Log.d("MULTIPLE SELECT", name)
                    imageNameList.add(name)
                    curFileList.add(uri)

                    photoObject = PhotoObject(curFileList)
                    madapter = UploadAdapter(photoObject, this)

                }
                Log.d("MULTPLE IMAG SIZE", imageNameList?.size.toString())

                if (madapter.itemCount <= 2){
                    madapter = UploadAdapter(photoObject,this)
                    binding.rvImg.adapter = madapter
                    binding.rvImg.adapter = madapter
                }else{
                    Toast.makeText(requireContext(),"Elige un maximo de 2 fotos",Toast.LENGTH_SHORT).show()
                    return
                }

            }else if(data?.data != null ){
                //Toast.makeText(requireContext(), "SELECCIONASTE 1 IMAGENE", Toast.LENGTH_SHORT).show()

                data?.data.let {
                    curFileList.add(it!!)
                    photoObject = PhotoObject(curFileList)
                    madapter = UploadAdapter(photoObject,this)
                    binding.rvImg.adapter = madapter
                }
            }

        }
    }

    override fun onSelectImgToDelete(pos: Int) {
        println(pos)
        photoObject.uri.removeAt(pos)
        madapter.notifyDataSetChanged()
    }

}