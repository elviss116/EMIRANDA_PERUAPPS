package com.emiranda.myapplication.di

import android.app.Application
import com.emiranda.myapplication.util.PreferencesUtil
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val preferencesModule = module {
    fun providerPreferenceUtil(app: Application) : PreferencesUtil{
        return PreferencesUtil(app)
    }

    single { providerPreferenceUtil(androidApplication()) }
}