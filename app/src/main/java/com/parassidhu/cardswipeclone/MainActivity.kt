package com.parassidhu.cardswipeclone

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidnetworking.AndroidNetworking
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private var currentItem = 1
    private var listOfItems = listOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init(savedInstanceState)
        initViewModel()
    }

    private fun init(bundle: Bundle?) {
        AndroidNetworking.initialize(applicationContext)
        count_tv.text = "1/8"

        currentItem = bundle?.getInt(CURRENT_ITEM) ?: 1

        swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
            .setDisplayViewCount(3)
            .setSwipeDecor(swipeDecor)

        left_btn.setOnClickListener {
            swipeView.doSwipe(false)
        }

        right_btn.setOnClickListener {
            swipeView.doSwipe(true)
        }

        swipeView.addItemRemoveListener {
            currentItem++
            count_tv.text = calculateCount()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        viewModel.getData().observe(this, Observer { list ->
           if (listOfItems.isEmpty()) {
               for (item in list) {
                   swipeView.addView(SwipeCard(item, swipeView))
               }
           }
        })
    }

    private val swipeDecor: SwipeDecor by lazy {
        SwipeDecor()
            .setPaddingTop(20)
            .setRelativeScale(0.01f)
    }

    private fun calculateCount(): String {
        if (currentItem > 8)
            return "All items have been swiped!"

        return "$currentItem/8"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.restart) {
            startOver()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun startOver() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_ITEM, currentItem)
    }
}