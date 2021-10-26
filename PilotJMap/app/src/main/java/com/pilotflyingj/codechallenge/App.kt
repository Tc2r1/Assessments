package com.pilotflyingj.codechallenge

import android.app.Application
import com.pilotflyingj.codechallenge.repository.MapRepository
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModel
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    /**
     * Custom Application class, here we can initalize debugging
     * libraries and other global libraries.
     *
     */
    override fun onCreate() {
        super.onCreate()
        // Init Debug Logger
        Timber.plant(Timber.DebugTree())

    }
}