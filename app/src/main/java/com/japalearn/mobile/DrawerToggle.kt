package com.japalearn.mobile

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout

class DrawerToggle(val activity: AppCompatActivity, val drawerLayout: DrawerLayout, val toolbar: Toolbar): ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
    override fun onDrawerClosed(drawerView: View) {
        super.onDrawerClosed(drawerView)
    }

    override fun onDrawerStateChanged(newState: Int) {
        super.onDrawerStateChanged(newState)
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        activity.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        super.onDrawerSlide(drawerView, slideOffset)
    }

}