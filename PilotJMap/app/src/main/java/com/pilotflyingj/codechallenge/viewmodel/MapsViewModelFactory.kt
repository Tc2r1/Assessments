package com.pilotflyingj.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pilotflyingj.codechallenge.repository.MapRepository

/**
 * A Factory class that generates a [MapsViewModel]
 * supplied with a reference to [MapRepository]
 */
class MapsViewModelFactory(var mapsRepository: MapRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(mapsRepository) as T
        }
        throw IllegalArgumentException("ViewModel Class Passed Is Not Required Type")
    }
}