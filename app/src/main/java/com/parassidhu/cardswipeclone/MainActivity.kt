package com.parassidhu.cardswipeclone

import android.os.Bundle
import android.util.Log
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
    private var isAdded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init(savedInstanceState)
        initViewModel()
    }

    private fun init(bundle: Bundle?) {
        AndroidNetworking.initialize(applicationContext)
        showLoading(true, false)
        count_tv.text = "1/8"

        currentItem = bundle?.getInt(CURRENT_ITEM) ?: 1

        count_tv.text = calculateCount()

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
            // User has started over. Hence all existing views
            // are removed and new ones are added
            if (isAdded) {
                swipeView.removeAllViews()
            }

            for (item in list) {
                if (!item.isSwiped)
                    swipeView.addView(SwipeCard(item, swipeView))
            }

            isAdded = true

            showLoading(false, list.isEmpty())
        })
    }

    private val swipeDecor: SwipeDecor by lazy {
        SwipeDecor()
            .setPaddingTop(20)
            .setRelativeScale(0.01f)
    }

    private fun calculateCount(): String {
        if (currentItem > 8)
            return getString(R.string.all_items_swiped_msg)

        return "$currentItem/8"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.restart) {
            startOver()
            toast(getString(R.string.restart_msg))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startOver() {
        viewModel.startOver()
        currentItem = 1
        count_tv.text = calculateCount()
    }

    private fun showLoading(boolean: Boolean, isError: Boolean) {
        if (isError) {
            title = "${resources.getString(R.string.app_name)} (Offline)"
            return
        }

        title = if (boolean)
            "Loading..."
        else
            resources.getString(R.string.app_name)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(CURRENT_ITEM, currentItem)
    }
}