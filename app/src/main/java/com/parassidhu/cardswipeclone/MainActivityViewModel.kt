package com.parassidhu.cardswipeclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private var liveData = MutableLiveData<List<Data>>()

    init {
        liveData = Repository.getData()
    }

    fun getData(): LiveData<List<Data>> {
        return liveData
    }
}