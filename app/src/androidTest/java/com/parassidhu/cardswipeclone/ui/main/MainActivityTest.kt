package com.parassidhu.cardswipeclone.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.parassidhu.cardswipeclone.R
import com.parassidhu.cardswipeclone.utils.Repository
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun Test_CardSwipe() {
        val rightButton = onView(
                withId(R.id.right_btn)
        )

        rightButton.perform(scrollTo(), click())

        val counterText = onView(
                withId(R.id.count_tv)
        )

        // Doesn't take into account when list has 1 element
        counterText.check(matches(withText(containsString("2/"))))
    }

    @Test
    fun Test_JSON_Parsing() {
        val list = Repository.loadData("{\"data\":[{\"id\":\"1\",\"text\":\"Hello\"}]}")
        assert(Pattern.matches(list[0].text, "Hello"))
        assert(Pattern.matches(list[0].id.toString(), "1"))
    }
}
