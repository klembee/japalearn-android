package com.japalearn.mobile.data.database.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(timestamp: Long?): Date?{
        if(timestamp == null) return null
        return Date(timestamp)
    }

    companion object {
        val FORMAT = SimpleDateFormat("y-M-d H-m-s", Locale.ENGLISH)

        init {
            FORMAT.timeZone = TimeZone.getTimeZone("UTC")
        }
    }
}