package com.japalearn.mobile.utils

import android.content.Context
import com.japalearn.mobile.R

object TimeUtil {

    fun durationToHumanReadable(context: Context, duration: Long): String{
        val minutes: Int = (duration / 60000).toInt()
        val hours: Int = minutes / 60
        val days: Int = hours / 24
        var months: Int = days / 30

        if(minutes < 60){
            return context.resources.getQuantityString(R.plurals.time_minutes, minutes, minutes)
        }else if(hours < 24){
            return context.resources.getQuantityString(R.plurals.time_hours, hours, hours)
        }else if(days < 30){
            return context.resources.getQuantityString(R.plurals.time_days, days, days)
        }else{
            return context.resources.getQuantityString(R.plurals.time_months, months, months)
        }


    }
}