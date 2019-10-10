package com.parassidhu.cardswipeclone

import android.util.Log
import android.widget.TextView
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.*

@Layout(R.layout.swipe_item)
class SwipeCard(var data: Data,
                var swipeView: SwipePlaceHolderView) {

    @View(R.id.text_tv)
    lateinit var textView: TextView

    @Resolve
    fun onResolved() {
        textView.text = data.text
    }

    @SwipeOut
    fun onSwipedOut() {
        Log.d("EVENT", "onSwipedOut")
        swipeView.addView(this)
    }

    @SwipeCancelState
    fun onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState")
    }

    @SwipeIn
    fun onSwipeIn() {
        Log.d("EVENT", "onSwipedIn")
    }

    @SwipeInState
    fun onSwipeInState() {
        Log.d("EVENT", "onSwipeInState")
    }

    @SwipeOutState
    fun onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState")
    }
}
