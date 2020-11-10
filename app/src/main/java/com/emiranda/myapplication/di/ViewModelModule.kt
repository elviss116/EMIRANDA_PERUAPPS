package com.emiranda.myapplication.di

import com.emiranda.myapplication.home.listPlaces.viewmodel.ListPlaceRoomViewModel
import com.emiranda.myapplication.home.listPlaces.viewmodel.ListPlaceViewModel
import com.emiranda.myapplication.login.viewModel.LoginViewModel
import com.emiranda.myapplication.myProfile.uploadImage.viewmodel.SavePlaceViewModel
import com.emiranda.myapplication.myProfile.uploadImage.viewmodel.UploadImageViewModel
import com.emiranda.myapplication.util.workmanager.viewmodel.GPSWorkerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {

    viewModel {
        UploadImageViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        SavePlaceViewModel(get())
    }
    viewModel {
        ListPlaceViewModel(get())
    }
    viewModel {
        ListPlaceRoomViewModel(get() )
    }
    viewModel {
        GPSWorkerViewModel(get())
    }
}