package com.parassidhu.cardswipeclone

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

object Repository {
    private val TAG = "Utils"
    private val URL =
        "https://gist.githubusercontent.com/anishbajpai014/d482191cb4fff429333c5ec64b38c197/raw/b11f56c3177a9ddc6649288c80a004e7df41e3b9/HiringTask.json"

    fun loadData(json: String): List<Data> {
        return try {
            val gson = GsonBuilder().create()
            val obj = JSONObject(json)
            val array = obj.getJSONArray("data")
            val list = ArrayList<Data>()
            Log.d(TAG, array.length().toString())

            for (i in 0 until array.length()) {
                val profile = gson.fromJson<Data>(array.getString(i), Data::class.java)
                list.add(profile)
            }
            list
        } catch (e: Exception) {
            e.printStackTrace()
            listOf()
        }
    }

    fun getData(): MutableLiveData<List<Data>> {
        val liveData = MutableLiveData<List<Data>>()

        Rx2AndroidNetworking.get(URL)
            .build()
            .stringSingle
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<String> {
                override fun onSuccess(result: String) {
                    val correctedJson = fixJson(result)
                    liveData.value = loadData(correctedJson)
                }

                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    Log.d(TAG, e.message)
                }
            })
        return liveData
    }

    private fun fixJson(string: String): String {
        val index = string.indexOf("/")
        return string.substring(index + 1)
    }
}