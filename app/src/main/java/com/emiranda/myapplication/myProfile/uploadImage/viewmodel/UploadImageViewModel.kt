package com.emiranda.myapplication.myProfile.uploadImage.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emiranda.myapplication.myProfile.uploadImage.mObject.PhotoObject
import com.emiranda.myapplication.myProfile.uploadImage.model.UploadImageDataSource
import com.emiranda.myapplication.util.MResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UploadImageViewModel(private val repository: UploadImageDataSource) : ViewModel() {

    private var _success = MutableLiveData<String>()
    val success : LiveData<String> = _success

    private var _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private var _listUri = MutableLiveData<MutableList<String>>()
    val listUri : LiveData<MutableList<String>> = _listUri

    var _saveProccess = MutableLiveData<Boolean>()
    val saveInProccess : LiveData<Boolean> = _saveProccess

    fun uploadImage(photoObject: PhotoObject, namePlace: String){

        try {
            if (photoObject.uri.isNullOrEmpty()){
                _error.value = "No ha seleccionado ninguna imagen"
                return
            }else if(namePlace.isNullOrEmpty()){
                _error.value = "Ingrese el nombre del lugar"
                return
            }else{
                _saveProccess.value = true
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO){
                        repository.uploadImage(photoObject)
                    }
                    when (result){
                        is MResource.Success -> {
                            if (result.data.isNullOrEmpty()){
                                _error.postValue("ERROR")
                            }else{
                                _listUri.postValue(result.data)
                            }
                        }
                        is MResource.Failure -> {
                            _error.postValue(result.exception.message)
                        }
                    }
                }
            }
        }catch (e: Exception){
            _error.value = e.localizedMessage
        }
    }
}