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

    fun startOver() {
        val list = liveData.value ?: return

        for (item in list) {
            item.isSwiped = false
        }

        liveData.value = list
    }
}