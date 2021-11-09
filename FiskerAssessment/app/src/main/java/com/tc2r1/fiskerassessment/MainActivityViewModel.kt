package com.tc2r1.fiskerassessment

import androidx.lifecycle.*

class MainActivityViewModel : ViewModel() {
    private val mySequence = Sequence()

    private val _resultSeq = MutableLiveData<String>()

    val resultSeq: LiveData<String>
    get() = _resultSeq


    init {
        _resultSeq.value = mySequence.getLongestSeqOld("abcdefxyzabcdef")
    }

}