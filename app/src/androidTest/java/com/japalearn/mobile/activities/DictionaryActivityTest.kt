package com.japalearn.mobile.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.japalearn.mobile.MainActivity
import com.japalearn.mobile.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class DictionaryActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun can_access_feedback_activity_from_dictionary(){
        goToDictionary()
        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.report_description_from_developer_txt)).check(matches(isDisplayed()))
    }

    private fun goToDictionary(){
        onView(withContentDescription("Open navigation drawer")).perform(click())
        onView(withText("Dictionary")).perform(click())
    }
}