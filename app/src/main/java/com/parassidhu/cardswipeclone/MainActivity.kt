package com.parassidhu.cardswipeclone

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnetworking.AndroidNetworking
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import com.parassidhu.cardswipeclone.Repository.loadData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initViewModel()
    }

    private fun init() {
        AndroidNetworking.initialize(applicationContext)

        swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
            .setDisplayViewCount(3)
            .setSwipeDecor(swipeDecor)

        left_btn.setOnClickListener {
            swipeView.doSwipe(false)
        }

        right_btn.setOnClickListener {
            swipeView.doSwipe(true)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.getData().observe(this, Observer { list ->
            for (item in list) {
                swipeView.addView(SwipeCard(item, swipeView))
            }
        })
    }

    private val swipeDecor: SwipeDecor by lazy {
        SwipeDecor()
            .setPaddingTop(20)
            .setRelativeScale(0.01f)
    }
}