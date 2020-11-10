package com.emiranda.myapplication.myProfile.uploadImage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emiranda.myapplication.myProfile.uploadImage.mObject.Place
import com.emiranda.myapplication.myProfile.uploadImage.model.SavePlaceDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavePlaceViewModel(private val repository : SavePlaceDataSource) : ViewModel() {

    private var _msgLD = MutableLiveData<String>()
    val msgWhenSaveLD : LiveData<String> = _msgLD

    fun savePlace(place: Place){
        viewModelScope.launch {
            val myMsg = withContext(Dispatchers.IO){
                repository.savePlace(place)
            }

            myMsg.let {
                _msgLD.postValue(it)
            }
        }
    }
}