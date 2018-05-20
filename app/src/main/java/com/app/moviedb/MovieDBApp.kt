package com.app.moviedb

import android.app.Application
import com.app.moviedb.presentation.di.Injector
import com.raizlabs.android.dbflow.config.FlowManager

class MovieDBApp:Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
        FlowManager.init(this)

    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}