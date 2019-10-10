package com.parassidhu.cardswipeclone.utils

import android.util.Log
import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.swipe.SwipeIn
import com.mindorks.placeholderview.annotations.swipe.SwipeOut
import com.parassidhu.cardswipeclone.R
import com.parassidhu.cardswipeclone.model.Data

@Layout(R.layout.swipe_item)
class SwipeCard(private var data: Data) {

    private val TAG = this.javaClass.simpleName

    @View(R.id.text_tv)
    lateinit var textView: TextView

    @Resolve
    fun onResolved() {
        if (::textView.isInitialized)
            textView.text = data.text
    }

    @SwipeOut
    fun onSwipedOut() {
        Log.d(TAG, "onSwipedOut")
        data.isSwiped = true
    }

    @SwipeIn
    fun onSwipeIn() {
        Log.d(TAG, "onSwipedIn")
        data.isSwiped = true
    }
}
