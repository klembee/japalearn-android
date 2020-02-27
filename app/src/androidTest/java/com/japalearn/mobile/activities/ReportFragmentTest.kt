package com.japalearn.mobile.activities

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.japalearn.mobile.R
import com.japalearn.mobile.ui.report.ReportFragment
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.Matchers.hasToString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class ReportFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(ReportFragment::class.java)

    @Test
    fun testChangeReportTypeKeepData(){
        chooseSuggestion()
        onView(withId(R.id.report_description_txt)).check(matches(withHint(R.string.bug_report_description_hint)))

        //Add text to the description
        onView(withId(R.id.report_description_txt)).perform(typeText("Hello from suggestion"))
        chooseBugReport()
        onView(withId(R.id.report_description_txt)).perform(typeText("Hello from bug report"))
        chooseSuggestion()
        onView(withId(R.id.report_description_txt)).check(matches(withText("Hello from suggestion")))
        chooseBugReport()
        onView(withId(R.id.report_description_txt)).check(matches(withText("Hello from bug report")))
    }

    @Test
    fun submitData(){
        //todo
    }

    private fun chooseSuggestion(){
        onView(withId(R.id.bug_report_type_spinner)).perform(click())
        onData(hasToString(startsWith("Report"))).perform(click())
    }

    private fun chooseBugReport(){
        onView(withId(R.id.bug_report_type_spinner)).perform(click())
        onData(hasToString(startsWith("Suggest"))).perform(click())
    }
}