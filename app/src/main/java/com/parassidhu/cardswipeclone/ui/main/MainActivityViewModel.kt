package com.parassidhu.cardswipeclone.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.parassidhu.cardswipeclone.model.Data
import com.parassidhu.cardswipeclone.utils.Repository

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