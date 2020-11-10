package com.emiranda.myapplication.di


import com.emiranda.myapplication.database.dao.PLACE_DAO
import com.emiranda.myapplication.home.listPlaces.model.ListPlaceDataSource
import com.emiranda.myapplication.home.listPlaces.model.ListPlaceRepository
import com.emiranda.myapplication.home.listPlaces.model.RemoteLocalRepository
import com.emiranda.myapplication.login.model.LoginDataSource
import com.emiranda.myapplication.login.model.LoginRepository
import com.emiranda.myapplication.myProfile.uploadImage.model.SavePlaceDataSource
import com.emiranda.myapplication.myProfile.uploadImage.model.SavePlaceRepository
import com.emiranda.myapplication.myProfile.uploadImage.model.UploadImageDataSource
import com.emiranda.myapplication.myProfile.uploadImage.model.UploadImageRepository
import com.emiranda.myapplication.util.workmanager.model.GPSWorkerDataSource
import com.emiranda.myapplication.util.workmanager.model.GPSWorkerRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import org.koin.dsl.module

val repositoryModule = module {

    fun provideUploadRepository(storageReference: StorageReference): UploadImageDataSource{
        return UploadImageRepository(storageReference)
    }

    fun providerLoginRepository(auth: FirebaseAuth, gsc : GoogleSignInClient) : LoginDataSource{
        return LoginRepository(auth,gsc)
    }

    fun providerSavePlaceRepository(firestore: FirebaseFirestore) : SavePlaceDataSource{
        return SavePlaceRepository(firestore)
    }

    fun providerListPlacesRepository(firestore: FirebaseFirestore) : ListPlaceDataSource {
        return ListPlaceRepository(firestore)
    }

    fun providerRemoteLocalRepository(dao: PLACE_DAO, repo : ListPlaceDataSource) : RemoteLocalRepository{
        return RemoteLocalRepository(dao,repo)
    }

    fun providerSaveUserRepository(firestore: FirebaseFirestore) : GPSWorkerDataSource{
        return GPSWorkerRepository(firestore)
    }

    single { provideUploadRepository( get() ) }
    single { providerLoginRepository( get(), get() ) }
    single { providerSavePlaceRepository( get() ) }
    single { providerListPlacesRepository(get()) }
    single { providerRemoteLocalRepository(get(), get()) }
    single { providerSaveUserRepository(get()) }

}