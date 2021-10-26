package com.pilotflyingj.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class AboutViewModel(aboutDescription: String) : ViewModel() {
    private var _aboutDescription = MutableLiveData<String>()

    // Encapsulation using Backing Properties
    val aboutDescription: LiveData<String> get() = _aboutDescription


    init {
        Timber.d("About View Model Created!")
        _aboutDescription.value = aboutDescription
    }
}